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
import kotlinx.css.Display.*
import kotlinx.css.FlexDirection.*
import react.*
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
                display = flex
                flexDirection = column
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

    private fun onInputChanged(text: String) =
        setState { query = text }

    private fun onSearchTriggered() =
        setState {
            results = null
            isSearching = true
        }

    private fun performSearch() {
        GlobalScope.launch {
            val searchResponse = http.get<SearchResponse>(Paths.search) {
                parameter("query", state.query)
            }
            setState {
                results = searchResponse
                isSearching = false
            }
        }
    }

    class State(
        var query: String,
        var results: SearchResponse?,
        var isSearching: Boolean
    ) : RState

}

fun RBuilder.search() {
    child(SearchComponent::class) {}
}
