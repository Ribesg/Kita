package fr.ribesg.kita.server.route

import io.ktor.routing.Routing

fun Routing.routes() {

    search()
    tmp()

    // Static content
    assets()

    // Catch-all
    index()

}
