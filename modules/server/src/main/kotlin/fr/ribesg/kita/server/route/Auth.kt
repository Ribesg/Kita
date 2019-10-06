@file:Suppress("EXPERIMENTAL_API_USAGE")

package fr.ribesg.kita.server.route

import fr.ribesg.kita.server.feature.user.UserService
import io.ktor.application.call
import io.ktor.locations.Location
import io.ktor.locations.post
import io.ktor.response.respond
import io.ktor.routing.Routing
import org.koin.ktor.ext.inject

@Location("/api/auth/login")
private data class LoginLocation(val login: String, val password: String)

@Location("/api/auth/refresh")
private data class RefreshLocation(val refreshToken: String)

@Location("/api/auth/register")
private data class RegisterLocation(val login: String, val password: String)

fun Routing.auth() {

    val user by inject<UserService>()

    post<LoginLocation> { (login, password) ->
        call.respond(user.login(login, password))
    }

    post<RefreshLocation> { (refreshToken) ->
        call.respond(user.refresh(refreshToken))
    }

    post<RegisterLocation> { (login, password) ->
        call.respond(user.register(login, password))
    }

}
