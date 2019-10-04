package fr.ribesg.kita.server.route

import fr.ribesg.kita.server.metadata.musicbrainz.MusicBrainzApi
import fr.ribesg.kita.server.metadata.musicbrainz.MusicBrainzArtist
import fr.ribesg.kita.server.metadata.tmdb.TmdbApi
import fr.ribesg.kita.server.metadata.tmdb.TmdbApiSearchMovieResult
import fr.ribesg.kita.server.metadata.tmdb.TmdbApiSearchTvResult
import fr.ribesg.kita.server.metadata.tvdb.TvdbApi
import fr.ribesg.kita.server.metadata.tvdb.TvdbApiSearchSeriesResult
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.util.getOrFail
import kotlinx.serialization.Serializable
import org.koin.ktor.ext.inject

@Suppress("EXPERIMENTAL_API_USAGE")
fun Routing.tmp() {

    val musicBrainz by inject<MusicBrainzApi>()
    val tmdb by inject<TmdbApi>()
    val tvdb by inject<TvdbApi>()

    get("/tmp") {
        val query = call.parameters.getOrFail("query")
        call.respond(
            TmpResult(
                tmdb.searchMovie(query),
                tmdb.searchTv(query),
                tvdb.searchSeries(query)
            )
        )
    }

    get("/tmp2") {
        call.respond(Tmp2Result(musicBrainz.searchArtist(call.parameters.getOrFail("query"))))
    }

}

@Serializable
private data class TmpResult(
    val tmdbMovie: List<TmdbApiSearchMovieResult>,
    val tmdbTv: List<TmdbApiSearchTvResult>,
    val tvdbSeries: List<TvdbApiSearchSeriesResult>
)

@Serializable
private data class Tmp2Result(
    val musicBrainzArtists: List<MusicBrainzArtist>
)
