@file:Suppress("EXPERIMENTAL_API_USAGE")

package fr.ribesg.kita.server.route

import fr.ribesg.kita.common.Paths
import fr.ribesg.kita.server.feature.user.UserService
import io.ktor.application.call
import io.ktor.request.receiveParameters
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import io.ktor.util.getOrFail
import org.koin.ktor.ext.inject

fun Routing.auth() {

    val user by inject<UserService>()

    post(Paths.Auth.login) {
        val params = call.receiveParameters()
        val login = params.getOrFail("login")
        val password = params.getOrFail("password")
        call.respond(user.login(login, password))
    }

    post(Paths.Auth.refresh) {
        val refreshToken = call.receiveParameters().getOrFail("refreshToken")
        call.respond(user.refresh(refreshToken))
    }

    post(Paths.Auth.register) {
        val params = call.receiveParameters()
        val login = params.getOrFail("login")
        val password = params.getOrFail("password")
        call.respond(user.register(login, password))
    }

}
