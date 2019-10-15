package fr.ribesg.kita.client.web.components.ui

import kotlinx.html.js.onClickFunction
import react.RBuilder
import styled.styledButton

fun RBuilder.button(
    text: String,
    enabled: Boolean = true,
    onButtonClicked: (() -> Unit)? = null
) {
    styledButton {
        +text
        attrs.disabled = !enabled
        onButtonClicked?.let { callback ->
            attrs.onClickFunction = { callback() }
        }
    }
}
