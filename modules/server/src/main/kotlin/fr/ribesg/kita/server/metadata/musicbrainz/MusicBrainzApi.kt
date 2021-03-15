package fr.ribesg.kita.server.metadata.musicbrainz

import com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import fr.ribesg.kita.server.MetaProperties
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import org.slf4j.LoggerFactory

interface MusicBrainzApi {

    suspend fun searchArtist(query: String): List<MusicBrainzArtist>

}

class MusicBrainzApiImpl(
    meta: MetaProperties
) : MusicBrainzApi {

    private val baseUrl = "https://musicbrainz.org/ws/2"
    private val userAgent = "Kita ${meta.version} ( ${meta.contact} )"

    private val http = HttpClient(CIO) {
        install(Logging) {
            logger = object : Logger {

                private val delegate = LoggerFactory.getLogger("MusicBrainz-Client")

                override fun log(message: String) {
                    delegate.info(message)
                }

            }
            level = LogLevel.INFO
        }
        defaultRequest {
            header("User-Agent", userAgent)
        }
    }

    override suspend fun searchArtist(query: String) =
        http.get<String>("$baseUrl/artist") {
            parameter("query", query)
        }.run {
            mapper.readValue<SearchArtistsResponse>(this).artists.artists.map { artist ->
                MusicBrainzArtist(
                    artist.lifeSpan?.begin,
                    artist.lifeSpan?.ended,
                    artist.gender,
                    artist.id,
                    artist.name,
                    artist.sortName
                )
            }
        }

    private val mapper =
        XmlMapper(
            JacksonXmlModule().apply {
                setDefaultUseWrapper(false)
            }
        ).apply {
            registerKotlinModule()
            configure(FAIL_ON_UNKNOWN_PROPERTIES, false)
        }

    @JacksonXmlRootElement(localName = "metadata")
    private data class SearchArtistsResponse(
        @JacksonXmlProperty(localName = "artist-list")
        val artists: SearchArtistsResponseArtistList
    )

    private data class SearchArtistsResponseArtistList(
        @JacksonXmlProperty(localName = "artist")
        val artists: List<SearchArtistsResponseArtist>
    )

    private data class SearchArtistsResponseArtist(
        @JacksonXmlProperty(isAttribute = true)
        val id: String,
        val gender: String?,
        @JacksonXmlProperty(localName = "life-span")
        val lifeSpan: SearchArtistsResponseArtistLifeSpan?,
        val name: String,
        @JacksonXmlProperty(localName = "sort-name")
        val sortName: String
    )

    private data class SearchArtistsResponseArtistLifeSpan(
        val begin: String?,
        val ended: String?
    )

}
