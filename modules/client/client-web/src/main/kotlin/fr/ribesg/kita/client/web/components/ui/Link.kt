@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components.ui

import kotlinx.css.TextAlign.center
import kotlinx.css.em
import kotlinx.css.fontSize
import kotlinx.css.textAlign
import kotlinx.html.js.onClickFunction
import react.RBuilder
import styled.css
import styled.styledA

fun RBuilder.Link(
    text: String,
    onClick: (() -> Unit)? = null
) {
    styledA {
        css {
            fontSize = .667.em
            textAlign = center
        }
        +text
        attrs.href = ""
        onClick?.let { callback ->
            attrs.onClickFunction = {
                it.preventDefault()
                callback()
            }
        }
    }
}
