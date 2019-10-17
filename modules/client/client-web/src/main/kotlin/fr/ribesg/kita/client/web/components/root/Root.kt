@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components.root

import fr.ribesg.kita.client.web.components.auth.Auth
import react.RBuilder

fun RBuilder.Root() {
    Auth("Authentification")
}
