package fr.ribesg.kita.server

import io.ktor.application.Application
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer

fun main() {
    @Suppress("EXPERIMENTAL_API_USAGE")
    embeddedServer(CIO, port = 12345, module = Application::kita).start(true)
}
