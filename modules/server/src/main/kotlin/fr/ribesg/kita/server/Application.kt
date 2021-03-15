@file:Suppress("EXPERIMENTAL_API_USAGE")

package fr.ribesg.kita.server

import fr.ribesg.kita.server.route.routes
import fr.ribesg.kita.server.util.HttpException
import fr.ribesg.kita.server.util.Jwt
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.BaseApplicationResponse.*
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.slf4j.LoggerFactory
import org.slf4j.event.Level

fun Application.kita() {

    install(AutoHeadResponse)
    install(Compression)
    install(DefaultHeaders)
    install(Locations)

    install(StatusPages) {
        val logger = LoggerFactory.getLogger("Status")
        exception<HttpException> {
            logger.error("Error", it)
            call.respond(it.status)
        }
        exception<Throwable> {
            logger.error("Error", it)
            try {
                call.respond(HttpStatusCode.InternalServerError)
            } catch (e: ResponseAlreadySentException) {
                // Ignored
            }
        }
    }

    install(ContentNegotiation) {
        json()
    }

    install(Koin) {
        modules(module(moduleDeclaration = Module::components))
    }

    install(Authentication) {
        val jwt by inject<Jwt>()
        jwt {
            realm = jwt.realm
            verifier(jwt.verifier)
            validate { credential ->
                if (jwt.audience in credential.payload.audience) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }

    install(CallLogging) {
        logger = LoggerFactory.getLogger("Ktor-Server")
        level = Level.INFO
    }

    routing(Routing::routes)

}
