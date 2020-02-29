@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components

import fr.ribesg.kita.client.common.Kita
import fr.ribesg.kita.client.web.AuthAction.SetAuthenticated
import fr.ribesg.kita.client.web.components.common.Button
import fr.ribesg.kita.client.web.useAuthStore
import kotlinx.css.Color
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.JustifyContent
import kotlinx.css.backgroundColor
import kotlinx.css.display
import kotlinx.css.em
import kotlinx.css.flexDirection
import kotlinx.css.height
import kotlinx.css.justifyContent
import kotlinx.css.padding
import react.RBuilder
import react.RProps
import react.child
import react.functionalComponent
import styled.css
import styled.styledDiv

fun RBuilder.toolbar() =
    child(ToolbarComponent)

private val ToolbarComponent = functionalComponent<RProps> {

    val (auth, authDispatch) = useAuthStore()

    styledDiv {
        css {
            height = 2.em
            padding = .5.em.value
            display = Display.flex
            flexDirection = FlexDirection.row
            justifyContent = JustifyContent.flexEnd
            backgroundColor = Color.lightGreen
        }

        if (auth.isAuthenticated) {
            Button("Logout") {
                Kita.auth.logout()
                authDispatch(SetAuthenticated(false))
            }
        }
    }

}
