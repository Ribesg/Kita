package fr.ribesg.kita.client.common

import fr.ribesg.kita.client.common.api.AuthApi
import fr.ribesg.kita.client.common.api.AuthApiImpl

object Apis {
    val auth: AuthApi = AuthApiImpl()
}
