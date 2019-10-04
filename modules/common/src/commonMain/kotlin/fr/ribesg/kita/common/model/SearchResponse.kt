package fr.ribesg.kita.common.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val movies: List<SearchResponseMovie>
)

@Serializable
data class SearchResponseMovie(
    val title: String,
    val description: String,
    val posterUrl: String?
)
