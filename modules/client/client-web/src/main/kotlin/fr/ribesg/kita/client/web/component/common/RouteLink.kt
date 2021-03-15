package fr.ribesg.kita.client.web.component.common

import react.RBuilder
import react.router.dom.routeLink

fun RBuilder.routeLink(
    text: String,
    dst: String
) {
    routeLink(
        to = dst,
        replace = false,
        className = "button is-text",
        handler = { +text }
    )
}
