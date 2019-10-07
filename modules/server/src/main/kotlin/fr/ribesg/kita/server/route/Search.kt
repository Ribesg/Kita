@file:Suppress("EXPERIMENTAL_API_USAGE")

package fr.ribesg.kita.server.route

import fr.ribesg.kita.common.Paths
import fr.ribesg.kita.common.model.SearchResponse
import fr.ribesg.kita.server.metadata.tmdb.TmdbApi
import fr.ribesg.kita.server.metadata.tmdb.toMovie
import io.ktor.application.call
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Routing
import org.koin.ktor.ext.inject

@Location(Paths.search)
data class SearchLocation(val query: String)

fun Routing.search() {

    val tmdb: TmdbApi by inject()

    get<SearchLocation> { (query) ->
        call.respond(SearchResponse(tmdb.searchMovie(query).map { tmdb.toMovie(it) }))
    }

} 
