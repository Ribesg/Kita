package fr.ribesg.kita.client.common.auth

interface AuthService {

    fun isAuthenticated(): Boolean

    fun getAccessToken(): String?

    suspend fun login(login: String, password: String)

    suspend fun refresh()

    suspend fun register(login: String, password: String)

    fun logout()

}

internal class AuthServiceImpl(
    private val authApi: AuthApi,
    private val authTokenStorage: AuthTokenStorage
) : AuthService {

    override fun isAuthenticated() =
        authTokenStorage.getAccessToken() != null

    override fun getAccessToken() =
        authTokenStorage.getAccessToken()

    override suspend fun login(login: String, password: String) {
        authTokenStorage.saveTokens(authApi.login(login, password))
    }

    override suspend fun refresh() {
        authTokenStorage.getRefreshToken()?.let { refreshToken ->
            authTokenStorage.saveTokens(authApi.refresh(refreshToken))
        }
    }

    override suspend fun register(login: String, password: String) {
        authTokenStorage.saveTokens(authApi.register(login, password))
    }

    override fun logout() {
        authTokenStorage.clearTokens()
    }

}
