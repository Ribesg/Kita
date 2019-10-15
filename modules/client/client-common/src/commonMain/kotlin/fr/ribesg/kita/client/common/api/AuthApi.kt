package fr.ribesg.kita.client.common.api

import fr.ribesg.kita.common.Paths
import fr.ribesg.kita.common.model.AuthTokens
import io.ktor.client.HttpClient
import io.ktor.client.features.json.Json
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.forms.submitForm
import io.ktor.http.Parameters

interface AuthApi {

    suspend fun login(login: String, password: String): AuthTokens

    suspend fun refresh(refreshToken: String): AuthTokens

    suspend fun register(login: String, password: String): AuthTokens

}

class AuthApiImpl : AuthApi {

    private val http = HttpClient {
        Json {
            serializer = KotlinxSerializer()
        }
    }

    override suspend fun login(login: String, password: String) =
        http.submitForm<AuthTokens>(Paths.Auth.login, Parameters.build {
            append("login", login)
            append("password", password)
        })

    override suspend fun refresh(refreshToken: String) =
        http.submitForm<AuthTokens>(Paths.Auth.refresh, Parameters.build {
            append("refreshToken", refreshToken)
        })

    override suspend fun register(login: String, password: String) =
        http.submitForm<AuthTokens>(Paths.Auth.register, Parameters.build {
            append("login", login)
            append("password", password)
        })

}
