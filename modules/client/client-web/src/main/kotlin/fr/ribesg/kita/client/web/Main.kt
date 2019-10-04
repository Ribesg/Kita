package fr.ribesg.kita.client.web

import react.dom.render
import kotlin.browser.document

fun main() =
    render(
        document.getElementById("kita")
            ?: throw IllegalStateException("div#kita not found")
    ) {
        search()
    }
