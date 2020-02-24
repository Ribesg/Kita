package fr.ribesg.kita.client.common

import fr.ribesg.kita.client.common.auth.AuthApiImpl
import fr.ribesg.kita.client.common.auth.AuthService
import fr.ribesg.kita.client.common.auth.AuthServiceImpl
import fr.ribesg.kita.client.common.auth.AuthTokenStorageImpl
import fr.ribesg.kita.client.common.search.SearchApi
import fr.ribesg.kita.client.common.search.SearchApiImpl

object Kita {
    val auth: AuthService = AuthServiceImpl(AuthApiImpl(), AuthTokenStorageImpl())
    val search: SearchApi = SearchApiImpl()
}
