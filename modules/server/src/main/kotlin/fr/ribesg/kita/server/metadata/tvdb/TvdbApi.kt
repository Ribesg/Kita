@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package fr.ribesg.kita.server.metadata.tvdb

import fr.ribesg.kita.server.util.JwtTokenHelper
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
import io.ktor.client.request.post
import io.ktor.http.ContentType.Application
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.slf4j.LoggerFactory
import java.time.Duration

interface TvdbApi {

    suspend fun searchSeries(query: String): List<TvdbApiSearchSeriesResult>

}

class TvdbApiImpl : TvdbApi {

    companion object {

        private const val API_KEY = "4144331619000000"
        private const val BASE_URL = "https://api.thetvdb.com"

        private const val SEARCH_FORBIDDEN_SERIES_ID = 313081

        private val TOKEN_REFRESH_THRESHOLD = Duration.ofHours(1).toMillis()

    }

    private var jwtToken: String? = null

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val http = HttpClient(CIO) {
        install(JsonFeature) {
            @Suppress("EXPERIMENTAL_API_USAGE")
            serializer = KotlinxSerializer(json = Json(JsonConfiguration(strictMode = false)))
        }
        install(Logging) {
            logger = object : Logger {

                private val delegate = LoggerFactory.getLogger("Tvdb-Client")

                override fun log(message: String) {
                    delegate.info(message)
                }

            }
            level = LogLevel.ALL
        }
        defaultRequest {
            jwtToken?.let {
                header("Authorization", "Bearer $jwtToken")
            }
        }
    }

    override suspend fun searchSeries(query: String) = doLoggedIn {
        http.get<TvdbSearchSeriesResults>("$BASE_URL/search/series") {
            parameter("name", query)
        }.data.filterNot { it.id == SEARCH_FORBIDDEN_SERIES_ID }
    }

    private suspend fun login() {
        jwtToken = http.post<TokenResponse>("$BASE_URL/login") {
            contentType(Application.Json)
            body = LoginPayload(API_KEY)
        }.token
    }

    private suspend fun refreshToken() {
        jwtToken = http.get<TokenResponse>("$BASE_URL/refresh_token").token
    }

    private suspend fun <R> doLoggedIn(task: suspend () -> R): R {
        val token = jwtToken
        if (token == null) {
            login()
        } else if (JwtTokenHelper.getRemainingTimeBeforeExpiration(token) < TOKEN_REFRESH_THRESHOLD) {
            refreshToken()
        }
        return task()
    }

    @Serializable
    private data class LoginPayload(val apikey: String)

    @Serializable
    private data class TokenResponse(val token: String)

    @Serializable
    private data class TvdbSearchSeriesResults(
        val data: List<TvdbApiSearchSeriesResult>
    )

}
