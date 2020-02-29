package fr.ribesg.kita.client.web.components.search

import fr.ribesg.kita.common.model.SearchResponseMovie
import kotlinx.css.Color
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.backgroundColor
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
import react.dom.h3
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
            display = Display.flex
            flexDirection = FlexDirection.row
            backgroundColor = Color.white
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
            h3 {
                +movie.title
            }
            p {
                +movie.description
            }
        }
    }

}
