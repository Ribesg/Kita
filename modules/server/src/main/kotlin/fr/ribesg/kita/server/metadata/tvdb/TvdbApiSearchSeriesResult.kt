package fr.ribesg.kita.server.metadata.tvdb

import kotlinx.serialization.Serializable

@Serializable
data class TvdbApiSearchSeriesResult(
    val aliases: List<String>,
    val banner: String,
    val firstAired: String,
    val id: Int,
    val network: String,
    val overview: String,
    val seriesName: String,
    val slug: String,
    val status: String
)

val TvdbApiSearchSeriesResult.fullBannerPath: String
    get() = "https://www.thetvdb.com/banners/$banner"
