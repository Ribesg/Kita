package fr.ribesg.kita.client.web.style

import kotlinx.css.*
import styled.StyleSheet

object KitaStyle : StyleSheet("kita", isStatic = true) {

    val center by css {
        height = 100.pct
        display = Display.flex
        justifyContent = JustifyContent.center
        alignItems = Align.center
    }

}
