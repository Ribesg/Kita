package fr.ribesg.kita.client.web.components.search

import fr.ribesg.kita.client.web.components.search.form.searchForm
import fr.ribesg.kita.client.web.components.search.results.searchResults
import fr.ribesg.kita.common.Paths
import fr.ribesg.kita.common.model.SearchResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.h1
import styled.css
import styled.styledDiv

class SearchComponent : RComponent<RProps, SearchComponent.State>() {

    private val http = HttpClient(Js) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    init {
        state = State("", null, false)
    }

    override fun RBuilder.render() {
        styledDiv {
            css {
                width = 100.pct
                maxHeight = 100.pct
                display = Display.flex
                flexDirection = FlexDirection.column
            }
            h1 { +"Search movies on TMDB" }
            searchForm(
                isInputEnabled = !state.isSearching,
                isButtonEnabled = state.query.isNotEmpty() && !state.isSearching,
                onInputChanged = ::onInputChanged,
                onSearchTriggered = ::onSearchTriggered
            )
            searchResults(state.results)
        }
    }

    private fun onInputChanged(text: String) {
        setState({ prev ->
            State(
                query = text,
                results = prev.results,
                isSearching = prev.isSearching
            )
        })
    }

    private fun onSearchTriggered() {
        setState({ prev ->
            State(
                query = prev.query,
                results = null,
                isSearching = true
            )
        }, ::performSearch)
    }

    private fun performSearch() {
        GlobalScope.launch {
            val searchResponse = http.get<SearchResponse>(Paths.search) {
                parameter("query", state.query)
            }
            setState({ prev ->
                State(
                    query = prev.query,
                    results = searchResponse,
                    isSearching = false
                )
            })
        }
    }

    data class State(
        var query: String,
        var results: SearchResponse?,
        var isSearching: Boolean
    ) : RState

}

fun RBuilder.search() {
    child(SearchComponent::class) {}
}
