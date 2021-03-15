package fr.ribesg.kita.client.web.component

import fr.ribesg.kita.client.web.component.common.routeButton
import fr.ribesg.kita.client.web.style.KitaStyle
import react.RBuilder
import react.RProps
import react.child
import react.functionalComponent
import styled.css
import styled.styledDiv

fun RBuilder.home() =
    child(HomeComponent)

private val HomeComponent = functionalComponent<RProps> {
    styledDiv {
        css { +KitaStyle.center }
        routeButton(
            text = "Login",
            dst = "/login",
            isPrimary = true
        )
    }
}
