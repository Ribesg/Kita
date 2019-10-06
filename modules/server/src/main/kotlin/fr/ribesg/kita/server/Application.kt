@file:Suppress("EXPERIMENTAL_API_USAGE")

package fr.ribesg.kita.server

import fr.ribesg.kita.server.route.routes
import fr.ribesg.kita.server.util.HttpException
import fr.ribesg.kita.server.util.Jwt
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.jwt.JWTPrincipal
import io.ktor.auth.jwt.jwt
import io.ktor.features.*
import io.ktor.locations.Locations
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.routing
import io.ktor.serialization.serialization
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.koin.Logger.slf4jLogger
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.slf4j.LoggerFactory

fun Application.kita() {

    install(AutoHeadResponse)
    install(Compression)
    install(DefaultHeaders)
    install(Locations)

    install(StatusPages) {
        exception<HttpException> { (status) ->
            call.respond(status)
        }
    }

    install(ContentNegotiation) {
        serialization(json = Json(JsonConfiguration.Stable))
    }

    install(Koin) {
        slf4jLogger()
        modules(module(moduleDeclaration = Module::components))
    }

    install(Authentication) {
        val jwt by inject<Jwt>()
        jwt {
            realm = jwt.realm
            verifier(jwt.verifier)
            validate { credential ->
                if (jwt.audience in credential.payload.audience) JWTPrincipal(credential.payload) else null
            }
        }
    }

    install(CallLogging) {
        logger = LoggerFactory.getLogger("Ktor-Server")
    }

    routing(Routing::routes)

}
