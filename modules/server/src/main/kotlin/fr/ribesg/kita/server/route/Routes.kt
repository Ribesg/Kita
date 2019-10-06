package fr.ribesg.kita.server.route

import io.ktor.routing.Routing

fun Routing.routes() {

    auth()
    search()

    // Static content
    assets()

    // Catch-all
    index()

}
