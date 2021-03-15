package fr.ribesg.kita.client.web

import fr.ribesg.kita.client.web.component.root
import kotlinx.browser.document
import react.RBuilder
import react.dom.render

fun main() {
    val root = document.getElementById("kita")
        ?: throw IllegalStateException("div#kita not found, cannot attach Kita applicationto DOM")
    render(root, handler = RBuilder::root)
}
