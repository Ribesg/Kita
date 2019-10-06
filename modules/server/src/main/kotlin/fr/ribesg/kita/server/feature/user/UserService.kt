@file:Suppress("EXPERIMENTAL_API_USAGE")

package fr.ribesg.kita.server.feature.user

import com.auth0.jwt.exceptions.AlgorithmMismatchException
import com.auth0.jwt.exceptions.InvalidClaimException
import com.auth0.jwt.exceptions.SignatureVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import fr.ribesg.kita.server.Database
import fr.ribesg.kita.server.feature.user.UserService.Tokens
import fr.ribesg.kita.server.util.Crypto
import fr.ribesg.kita.server.util.HttpException
import fr.ribesg.kita.server.util.Jwt
import io.ktor.features.BadRequestException
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable
import java.util.*

interface UserService {

    fun login(login: String, password: String): Tokens

    fun refresh(refreshToken: String): Tokens

    fun register(login: String, password: String): Tokens

    @Serializable
    data class Tokens(val accessToken: String, val refreshToken: String)

}

class UserServiceImpl(
    database: Database,
    private val jwt: Jwt
) : UserService {

    private val userQueries = database.userQueries

    override fun login(login: String, password: String): Tokens {
        val user = userQueries.getUserByLogin(login).executeAsOneOrNull()
            ?: throw BadRequestException("Bad credentials")
        if (!Crypto.check(password, user.passwordSalt, user.passwordHash))
            throw BadRequestException("Bad credentials")
        return newTokens(user.id)
    }

    override fun refresh(refreshToken: String): Tokens {
        val token = try {
            jwt.verifier.verify(refreshToken)
        } catch (e: Throwable) {
            if (e is AlgorithmMismatchException ||
                e is SignatureVerificationException ||
                e is TokenExpiredException ||
                e is InvalidClaimException
            ) {
                throw BadRequestException("Invalid refresh token")
            } else {
                throw Error("Failed to verify refresh token", e)
            }
        }
        return newTokens(token.claims["id"]!!.asString())
    }

    override fun register(login: String, password: String): Tokens {
        if (userQueries.getUserByLogin(login).executeAsOneOrNull() != null)
            throw HttpException(HttpStatusCode.Conflict)
        val id = UUID.randomUUID().toString()
        val (salt, hash) = Crypto.hashNewPassword(password)
        userQueries.createUser(
            id = id,
            login = login,
            displayName = null,
            passwordSalt = salt,
            passwordHash = hash
        )
        return newTokens(id)
    }

    private fun newTokens(id: String): Tokens =
        Tokens(jwt.newAccessToken(id), jwt.newRefreshToken(id))

}
