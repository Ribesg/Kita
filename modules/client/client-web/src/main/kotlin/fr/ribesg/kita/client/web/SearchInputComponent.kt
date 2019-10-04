package fr.ribesg.kita.client.web

import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.display
import kotlinx.css.flexDirection
import kotlinx.html.INPUT
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onKeyDownFunction
import org.w3c.dom.events.KeyboardEvent
import react.RBuilder
import react.dom.button
import react.dom.input
import styled.css
import styled.styledDiv

fun RBuilder.searchInput(
    isInputEnabled: Boolean,
    isButtonEnabled: Boolean,
    onInputChanged: (String) -> Unit,
    onSearchTriggered: () -> Unit
) =
    styledDiv {
        css {
            display = Display.flex
            flexDirection = FlexDirection.row
        }
        input {
            attrs.disabled = !isInputEnabled
            attrs.onChangeFunction = {
                onInputChanged(it.target.unsafeCast<INPUT>().value)
            }
            attrs.onKeyDownFunction = {
                val event = it.asDynamic().nativeEvent as KeyboardEvent
                if (event.key == "Enter") onSearchTriggered()
            }
        }
        button {
            attrs.disabled = !isButtonEnabled
            attrs.onClickFunction = {
                onSearchTriggered()
            }
            +"Search"
        }
    }
