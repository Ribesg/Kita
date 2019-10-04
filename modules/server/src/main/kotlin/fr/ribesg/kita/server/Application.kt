package fr.ribesg.kita.server

import fr.ribesg.kita.server.route.routes
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.routing.Routing
import io.ktor.routing.routing
import io.ktor.serialization.serialization
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.koin.Logger.slf4jLogger
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.slf4j.LoggerFactory

fun Application.kita() {

    install(DefaultHeaders)
    install(AutoHeadResponse)
    install(Compression)

    install(ContentNegotiation) {
        serialization(json = Json(JsonConfiguration.Stable))
    }

    install(Koin) {
        slf4jLogger()
        modules(module(moduleDeclaration = Module::components))
    }

    install(CallLogging) {
        logger = LoggerFactory.getLogger("Ktor-Server")
    }

    routing(Routing::routes)

}
