@file:Suppress("EXPERIMENTAL_API_USAGE")

package fr.ribesg.kita.server.route

import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.routing.Routing

fun Routing.assets() {
    static("assets") {
        resources("assets")
    }
}
