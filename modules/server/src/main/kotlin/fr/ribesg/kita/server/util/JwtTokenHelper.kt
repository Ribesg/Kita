package fr.ribesg.kita.server.util

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.time.Instant
import java.util.*

object JwtTokenHelper {

    @Suppress("EXPERIMENTAL_API_USAGE")
    private val json = Json(JsonConfiguration(strictMode = false))

    fun getRemainingTimeBeforeExpiration(token: String): Long =
        decode(token).second.expirationTime * 1000 - Instant.now().toEpochMilli()

    private fun decode(token: String): Pair<JwtTokenHeader, JwtTokenPayload> {
        val split = token.split('.')
        require(split.size == 3) { "Invalid JWT token: $token" }
        val (encodedHeader, encodedPayload) = split
        val rawHeader = String(Base64.getDecoder().decode(encodedHeader))
        val rawPayload = String(Base64.getDecoder().decode(encodedPayload))
        val header = json.parse(JwtTokenHeader.serializer(), rawHeader)
        val payload = json.parse(JwtTokenPayload.serializer(), rawPayload)
        return header to payload
    }

    @Serializable
    private data class JwtTokenHeader(
        @SerialName("alg")
        val algorithm: String,
        @SerialName("typ")
        val type: String
    )

    @Serializable
    private data class JwtTokenPayload(
        @SerialName("exp")
        val expirationTime: Long,
        @SerialName("iat")
        val issuedAt: Long
    )

}
