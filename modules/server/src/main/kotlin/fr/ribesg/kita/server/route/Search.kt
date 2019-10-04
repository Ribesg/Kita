@file:Suppress("EXPERIMENTAL_API_USAGE")

package fr.ribesg.kita.server.route

import fr.ribesg.kita.common.model.SearchResponse
import fr.ribesg.kita.server.metadata.tmdb.TmdbApi
import fr.ribesg.kita.server.metadata.tmdb.toMovie
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.util.getOrFail
import org.koin.ktor.ext.inject

fun Routing.search() {

    val tmdb: TmdbApi by inject()

    get("/search") {
        val query = call.parameters.getOrFail("query")
        val movies = tmdb.searchMovie(query).map { tmdb.toMovie(it) }
        call.respond(SearchResponse(movies))
    }

} 
