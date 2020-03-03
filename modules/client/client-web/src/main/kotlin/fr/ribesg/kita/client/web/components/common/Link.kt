@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components.common

import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.dom.a

fun RBuilder.Link(
    text: String,
    onClick: (() -> Unit)? = null
) {
    a("", classes = "level") {
        +text
        onClick?.let { callback ->
            attrs.onClickFunction = {
                it.preventDefault()
                callback()
            }
        }
    }
}
