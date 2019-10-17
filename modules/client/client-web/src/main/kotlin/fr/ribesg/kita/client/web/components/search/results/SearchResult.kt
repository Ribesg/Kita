package fr.ribesg.kita.client.web.components.search.results

import fr.ribesg.kita.common.model.SearchResponseMovie
import kotlinx.css.*
import kotlinx.css.Display.*
import kotlinx.css.FlexDirection.*
import react.RBuilder
import react.dom.h3
import react.dom.p
import styled.css
import styled.styledDiv
import styled.styledImg

fun RBuilder.searchResult(result: SearchResponseMovie) {
    styledDiv {
        css {
            display = flex
            flexDirection = row
            margin = 5.px.value
        }
        styledImg(src = result.posterUrl) {
            css {
                width = 200.px
                height = 300.px
                marginRight = 5.px
                flex(flexShrink = .0)
            }
        }
        styledDiv {
            css {
                display = flex
                flexDirection = column
            }
            h3 { +result.title }
            p { +result.description }
        }
    }
}
