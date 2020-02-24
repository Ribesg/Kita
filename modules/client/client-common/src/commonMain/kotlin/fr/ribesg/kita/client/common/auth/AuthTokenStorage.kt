package fr.ribesg.kita.client.common.auth

import fr.ribesg.kita.common.model.AuthTokens

interface AuthTokenStorage {

    fun saveTokens(tokens: AuthTokens)

    fun getAccessToken(): String?

    fun getRefreshToken(): String?

}

internal expect class AuthTokenStorageImpl() : AuthTokenStorage
