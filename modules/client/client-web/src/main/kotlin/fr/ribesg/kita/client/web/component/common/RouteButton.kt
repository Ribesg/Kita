package fr.ribesg.kita.client.web.component.common

import react.RBuilder
import react.router.dom.routeLink

fun RBuilder.routeButton(
    text: String,
    dst: String,
    isDisabled: Boolean = false,
    isInverted: Boolean = false,
    isPrimary: Boolean = false
) {
    val classes = mutableListOf("button")
    if (isInverted) classes += "is-inverted"
    if (isDisabled) classes += "is-disabled"
    if (isPrimary) classes += "is-primary"
    routeLink(
        to = dst,
        replace = false,
        className = classes.joinToString(" "),
        handler = { +text }
    )
}
