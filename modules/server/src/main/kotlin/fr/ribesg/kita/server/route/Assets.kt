@file:Suppress("EXPERIMENTAL_API_USAGE")

package fr.ribesg.kita.server.route

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File
import java.time.Duration

fun Routing.assets() {
    val oneYearInSeconds = Duration.ofDays(365).toSeconds()
    val pathParameterName = "path"
    get("/assets/{$pathParameterName...}") {
        val relativePath = call.parameters
            .getAll(pathParameterName)
            ?.joinToString(File.separator)
            ?: return@get

        val content = call.resolveResource(relativePath, "assets") { extension ->
            when {
                extension.endsWith(".js.map") -> ContentType.Application.Json
                else -> ContentType.defaultForFileExtension(extension)
            }
        } ?: return@get

        call.response.header(HttpHeaders.CacheControl, "public, max-age=$oneYearInSeconds, immutable")
        call.respond(content)
    }
}
