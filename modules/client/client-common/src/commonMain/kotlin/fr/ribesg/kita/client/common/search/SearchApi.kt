package fr.ribesg.kita.client.common.search

import fr.ribesg.kita.client.common.http
import fr.ribesg.kita.common.Paths
import fr.ribesg.kita.common.model.SearchResponse
import io.ktor.client.request.get
import io.ktor.client.request.parameter

interface SearchApi {

    suspend fun search(query: String): SearchResponse

}

internal class SearchApiImpl : SearchApi {

    override suspend fun search(query: String): SearchResponse =
        http.get(Paths.search) {
            parameter("query", query)
        }

}
