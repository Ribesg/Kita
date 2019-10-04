package fr.ribesg.kita.client.web

import fr.ribesg.kita.client.web.ReactiveTitleComponent.Props
import fr.ribesg.kita.client.web.ReactiveTitleComponent.State
import kotlinx.css.*
import kotlinx.html.js.onClickFunction
import react.*
import styled.css
import styled.styledH1

class ReactiveTitleComponent(props: Props) : RComponent<Props, State>(props) {

    init {
        state = State(true)
    }

    override fun RBuilder.render() {
        styledH1 {
            css {
                color = if (state.isGreen) Color.green else Color.red
                cursor = Cursor.pointer
                userSelect = UserSelect.none
            }
            attrs.onClickFunction = {
                setState { isGreen = !state.isGreen }
            }
            +props.text
        }
    }

    data class Props(var text: String) : RProps

    data class State(var isGreen: Boolean) : RState

}

fun RBuilder.reactiveTitle(text: String) =
    child(ReactiveTitleComponent::class) {
        attrs.text = text
    }
