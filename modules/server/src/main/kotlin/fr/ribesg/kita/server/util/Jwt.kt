package fr.ribesg.kita.server.util

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import fr.ribesg.kita.server.EnvProperties
import fr.ribesg.kita.server.MetaProperties
import java.time.Duration
import java.time.Instant
import java.util.*

interface Jwt {
    val audience: String
    val realm: String
    val verifier: JWTVerifier
    fun newAccessToken(id: String): String
    fun newRefreshToken(id: String): String
}

class JwtImpl(
    env: EnvProperties,
    meta: MetaProperties
) : Jwt {

    companion object {
        private const val SUBJECT = "Authentication"
        private val DEFAULT_TOKEN_DURATION_ACCESS = Duration.ofHours(1)
        private val DEFAULT_TOKEN_DURATION_REFRESH = Duration.ofDays(7)
    }

    override val audience = env.jwtAudience ?: "${meta.name}-debug-audience"
    override val realm = env.jwtRealm ?: "${meta.name}-debug-realm"

    private val issuer = env.jwtIssuer ?: "${meta.name}-debug-issuer"
    private val secret = env.jwtSecret ?: "${meta.name}-debug-secret"

    private val accessDuration = env.jwtTokenDurationAccess ?: DEFAULT_TOKEN_DURATION_ACCESS
    private val refreshDuration = env.jwtTokenDurationRefresh ?: DEFAULT_TOKEN_DURATION_REFRESH

    private val algorithm = Algorithm.HMAC512(secret)

    override val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withAudience(audience)
        .withIssuer(issuer)
        .withSubject(SUBJECT)
        .build()

    override fun newAccessToken(id: String): String =
        newToken(id, accessDuration)

    override fun newRefreshToken(id: String): String =
        newToken(id, refreshDuration)

    private fun newToken(id: String, duration: Duration) = JWT
        .create()
        .withAudience(audience)
        .withClaim("id", id)
        .withExpiresAt(Date.from(Instant.now().plus(duration)))
        .withIssuedAt(Date())
        .withIssuer(issuer)
        .withSubject(SUBJECT)
        .sign(algorithm)

}
