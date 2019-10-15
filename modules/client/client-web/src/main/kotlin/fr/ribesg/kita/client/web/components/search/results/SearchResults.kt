package fr.ribesg.kita.client.web.components.search.results

import fr.ribesg.kita.common.model.SearchResponse
import kotlinx.css.*
import react.RBuilder
import styled.css
import styled.styledDiv

fun RBuilder.searchResults(results: SearchResponse?) {
    styledDiv {
        css {
            display = Display.flex
            flexDirection = FlexDirection.column
            padding = 5.px.value
        }
        results?.movies?.forEach { searchResult(it) }
    }
}
