@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components

import fr.ribesg.kita.client.web.components.search.search
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.display
import kotlinx.css.flexDirection
import kotlinx.css.flexGrow
import react.RBuilder
import react.RProps
import react.child
import react.functionalComponent
import styled.css
import styled.styledDiv

fun RBuilder.content() =
    child(ContentComponent)

private val ContentComponent = functionalComponent<RProps> {

    styledDiv {
        css {
            display = Display.flex
            flexDirection = FlexDirection.column
        }
        search()
    }

}
