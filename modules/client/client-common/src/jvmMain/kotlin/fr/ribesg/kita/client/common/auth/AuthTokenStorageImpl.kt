package fr.ribesg.kita.client.common.auth

import fr.ribesg.kita.common.model.AuthTokens
import java.io.File

internal actual class AuthTokenStorageImpl : AuthTokenStorage {

    companion object {
        private const val FILE_ACCESS_TOKEN = "accessToken.jwt"
        private const val FILE_REFRESH_TOKEN = "refreshToken.jwt"
    }

    private var accessToken: String? = null

    override fun saveTokens(tokens: AuthTokens) {
        accessToken = tokens.accessToken
        File(FILE_ACCESS_TOKEN).writeText(tokens.accessToken)
        File(FILE_REFRESH_TOKEN).writeText(tokens.refreshToken)
    }

    override fun getAccessToken() =
        accessToken ?: try {
            File(FILE_ACCESS_TOKEN).readText()
        } catch (t: Throwable) {
            null
        }

    override fun getRefreshToken() =
        try {
            File(FILE_REFRESH_TOKEN).readText()
        } catch (t: Throwable) {
            null
        }

    override fun clearTokens() {
        File(FILE_ACCESS_TOKEN).delete()
        File(FILE_REFRESH_TOKEN).delete()
    }

}
