package fr.ribesg.kita.client.common.auth

import fr.ribesg.kita.client.common.baseUrl
import fr.ribesg.kita.client.common.http
import fr.ribesg.kita.common.Paths
import fr.ribesg.kita.common.model.AuthTokens
import io.ktor.client.request.forms.submitForm
import io.ktor.http.Parameters

interface AuthApi {

    suspend fun login(login: String, password: String): AuthTokens

    suspend fun refresh(refreshToken: String): AuthTokens

    suspend fun register(login: String, password: String): AuthTokens

}

internal class AuthApiImpl : AuthApi {

    override suspend fun login(login: String, password: String) =
        http.submitForm<AuthTokens>(baseUrl + Paths.Auth.login, Parameters.build {
            append("login", login)
            append("password", password)
        })

    override suspend fun refresh(refreshToken: String) =
        http.submitForm<AuthTokens>(baseUrl + Paths.Auth.refresh, Parameters.build {
            append("refreshToken", refreshToken)
        })

    override suspend fun register(login: String, password: String) =
        http.submitForm<AuthTokens>(baseUrl + Paths.Auth.register, Parameters.build {
            append("login", login)
            append("password", password)
        })

}
