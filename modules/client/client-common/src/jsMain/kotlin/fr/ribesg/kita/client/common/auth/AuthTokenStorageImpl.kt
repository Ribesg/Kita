package fr.ribesg.kita.client.common.auth

import fr.ribesg.kita.common.model.AuthTokens
import kotlinx.browser.localStorage

internal actual class AuthTokenStorageImpl : AuthTokenStorage {

    companion object {
        private const val KEY_ACCESS_TOKEN = "jwtAccessToken"
        private const val KEY_REFRESH_TOKEN = "jwtRefreshToken"
    }

    override fun saveTokens(tokens: AuthTokens) {
        localStorage.setItem(KEY_ACCESS_TOKEN, tokens.accessToken)
        localStorage.setItem(KEY_REFRESH_TOKEN, tokens.refreshToken)
    }

    override fun getAccessToken() =
        localStorage.getItem(KEY_ACCESS_TOKEN)

    override fun getRefreshToken() =
        localStorage.getItem(KEY_REFRESH_TOKEN)

    override fun clearTokens() {
        localStorage.removeItem(KEY_ACCESS_TOKEN)
        localStorage.removeItem(KEY_REFRESH_TOKEN)
    }

}
