package fr.ribesg.kita.client.web.components.search

import fr.ribesg.kita.common.model.SearchResponseMovie
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.display
import kotlinx.css.em
import kotlinx.css.flexDirection
import kotlinx.css.flexShrink
import kotlinx.css.height
import kotlinx.css.padding
import kotlinx.css.width
import react.RBuilder
import react.RProps
import react.child
import react.dom.h5
import react.dom.p
import react.functionalComponent
import styled.css
import styled.styledDiv
import styled.styledImg

fun RBuilder.searchResult(movie: SearchResponseMovie) =
    child(SearchResultComponent) {
        attrs.movie = movie
    }

private interface SearchResultProps : RProps {
    var movie: SearchResponseMovie
}

private val SearchResultComponent = functionalComponent<SearchResultProps> { props ->

    val movie = props.movie

    styledDiv {
        css {
            height = 15.em
            display = Display.flex
            flexDirection = FlexDirection.row
        }
        styledImg(src = movie.posterUrl) {
            key = movie.posterUrl ?: ""
            css {
                width = 10.em
                height = 15.em
                flexShrink = .0
            }
        }
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                padding = 1.em.value
            }
            h5("matter-h5") {
                +movie.title
            }
            p("matter-body1") {
                +movie.description
            }
        }
    }

}
