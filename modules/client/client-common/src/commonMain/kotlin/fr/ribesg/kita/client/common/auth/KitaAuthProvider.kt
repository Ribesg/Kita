package fr.ribesg.kita.client.common.auth

import fr.ribesg.kita.client.common.Kita
import io.ktor.client.features.auth.AuthProvider
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.auth.HttpAuthHeader

internal object KitaAuthProvider : AuthProvider {

    override val sendWithoutRequest = false

    override suspend fun addRequestHeaders(request: HttpRequestBuilder) {
        Kita.auth.getAccessToken()?.let { accessToken ->
            request.header("Authorization", "Bearer $accessToken")
        }
    }

    override fun isApplicable(auth: HttpAuthHeader) = auth.authScheme == "Bearer"

}
