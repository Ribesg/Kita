package fr.ribesg.kita.server.metadata.musicbrainz

import kotlinx.serialization.Serializable

@Serializable
data class MusicBrainzArtist(
    val birth: String?,
    val death: String?,
    val gender: String?,
    val id: String,
    val name: String,
    val sortName: String
)
