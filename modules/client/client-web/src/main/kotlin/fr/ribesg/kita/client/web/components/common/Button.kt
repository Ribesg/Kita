@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components.common

import kotlinx.html.classes
import kotlinx.html.js.onClickFunction
import react.RBuilder
import styled.styledButton

fun RBuilder.Button(
    text: String,
    type: String = "container",
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    require(type in listOf("container", "outlined", "text", "unelevated")) {
        "Button.type should be one of container/outlined/text/unelevated, invalid value $type"
    }
    styledButton {
        +text
        attrs.classes += "matter-button-$type"
        attrs.disabled = !enabled
        onClick?.let { callback ->
            attrs.onClickFunction = { callback() }
        }
    }
}
