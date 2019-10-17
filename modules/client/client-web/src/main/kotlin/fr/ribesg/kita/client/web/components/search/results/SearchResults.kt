package fr.ribesg.kita.client.web.components.search.results

import fr.ribesg.kita.common.model.SearchResponse
import kotlinx.css.Display.flex
import kotlinx.css.FlexDirection.column
import kotlinx.css.display
import kotlinx.css.flexDirection
import kotlinx.css.padding
import kotlinx.css.px
import react.RBuilder
import styled.css
import styled.styledDiv

fun RBuilder.searchResults(results: SearchResponse?) {
    styledDiv {
        css {
            display = flex
            flexDirection = column
            padding = 5.px.value
        }
        results?.movies?.forEach { searchResult(it) }
    }
}
