@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components.common

import kotlinx.html.classes
import kotlinx.html.js.onClickFunction
import react.RBuilder
import styled.styledButton

fun RBuilder.Button(
    text: String,
    isDisabled: Boolean = false,
    isInverted: Boolean = false,
    isPrimary: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    styledButton {
        +text
        attrs.classes += "button"
        if (isInverted) attrs.classes += "is-inverted"
        if (isPrimary) attrs.classes += "is-primary"
        attrs.disabled = isDisabled
        onClick?.let { callback ->
            attrs.onClickFunction = { callback() }
        }
    }
}
