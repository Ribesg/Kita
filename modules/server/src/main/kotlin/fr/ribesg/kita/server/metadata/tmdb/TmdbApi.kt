package fr.ribesg.kita.server.metadata.tmdb

import fr.ribesg.kita.server.metadata.tmdb.TmdbImageType.*
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.temporal.ChronoUnit

interface TmdbApi {

    suspend fun getMovie(id: Int): TmdbApiMovie

    suspend fun searchMovie(query: String): List<TmdbApiSearchMovieResult>

    suspend fun searchTv(query: String): List<TmdbApiSearchTvResult>

    // TODO Should be in service and should be using getConfiguration from API
    suspend fun getImageFullPath(path: String, type: TmdbImageType, widerThan: Int? = null): String

}

class TmdbApiImpl : TmdbApi {

    companion object {

        private const val API_KEY = "ee7c7baeb9f2be87f4fd495141e7357b"
        private const val API_TOKEN =
            "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlZTdjN2JhZWI5ZjJiZTg3ZjRmZDQ5NTE0MWU3MzU3YiIsInN1YiI6IjVkMjlmY2E5Y2FhYjZkNjY1OTlkZjhlZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.l511By8yOsj1fKFDqwhesm8TmALcE80Uy5xYpPP3MQ8"

        private const val BASE_URL_3 = "https://api.themoviedb.org/3"
        private const val BASE_URL_4 = "https://api.themoviedb.org/4"

    }

    private var lastConfigurationRetrieval: Instant = Instant.EPOCH
    private lateinit var configuration: TmdbConfiguration

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val http = HttpClient(CIO) {
        install(JsonFeature) {
            @Suppress("EXPERIMENTAL_API_USAGE")
            serializer = KotlinxSerializer(json = Json(JsonConfiguration(strictMode = false)))
        }
        install(Logging) {
            logger = object : Logger {

                private val delegate = LoggerFactory.getLogger("Tmdb-Client")

                override fun log(message: String) {
                    delegate.info(message)
                }

            }
            level = LogLevel.INFO
        }
        defaultRequest {
            header("Authorization", "Bearer $API_TOKEN")
            parameter("api_key", API_KEY)
        }
    }

    override suspend fun getMovie(id: Int) =
        http.get<TmdbApiMovie>("$BASE_URL_3/movie/$id")

    override suspend fun searchMovie(query: String) =
        http.get<TmdbApiSearchMovieResults>("$BASE_URL_3/search/movie") {
            parameter("query", query)
        }.results

    override suspend fun searchTv(query: String) =
        http.get<TmdbApiSearchTvResults>("$BASE_URL_3/search/tv") {
            parameter("query", query)
        }.results

    override suspend fun getImageFullPath(path: String, type: TmdbImageType, widerThan: Int?): String {
        val config = getConfiguration().images
        val sizes = when (type) {
            BACKDROP -> config.backdropSizes
            LOGO -> config.logoSizes
            POSTER -> config.posterSizes
            PROFILE -> config.profileSizes
            STILL -> config.stillSizes
        }
        val size =
            if (widerThan == null) {
                sizes.last()
            } else {
                sizes.firstOrNull {
                    it.substring(1).toInt() > widerThan
                } ?: sizes.last()
            }
        return "${config.secureBaseUrl}$size$path"
    }

    private suspend fun getConfiguration(): TmdbConfiguration {
        if (lastConfigurationRetrieval < Instant.now().minus(24, ChronoUnit.HOURS)) {
            configuration = http.get<TmdbConfiguration>("$BASE_URL_3/configuration")
            lastConfigurationRetrieval = Instant.now()
        }
        return configuration
    }

    @Serializable
    private data class TmdbApiSearchMovieResults(
        val results: List<TmdbApiSearchMovieResult>
    )

    @Serializable
    private data class TmdbApiSearchTvResults(
        val results: List<TmdbApiSearchTvResult>
    )

    @Serializable
    private data class TmdbConfiguration(
        @SerialName("change_keys")
        val changeKeys: List<String>,
        val images: TmdbImagesConfiguration
    )

    @Serializable
    private data class TmdbImagesConfiguration(
        @SerialName("backdrop_sizes")
        val backdropSizes: List<String>,
        @SerialName("base_url")
        val baseUrl: String,
        @SerialName("logo_sizes")
        val logoSizes: List<String>,
        @SerialName("poster_sizes")
        val posterSizes: List<String>,
        @SerialName("profile_sizes")
        val profileSizes: List<String>,
        @SerialName("still_sizes")
        val stillSizes: List<String>,
        @SerialName("secure_base_url")
        val secureBaseUrl: String
    )

}
