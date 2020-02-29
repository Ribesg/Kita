@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components.common

import kotlinx.html.js.onClickFunction
import react.RBuilder
import styled.styledButton

fun RBuilder.Button(
    text: String,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null
) {
    styledButton {
        +text
        attrs.disabled = !enabled
        onClick?.let { callback ->
            attrs.onClickFunction = { callback() }
        }
    }
}
