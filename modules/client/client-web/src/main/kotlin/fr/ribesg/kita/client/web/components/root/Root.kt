@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components.root

import fr.ribesg.kita.client.common.Kita
import fr.ribesg.kita.client.web.components.auth.Auth
import fr.ribesg.kita.client.web.components.search.Search
import react.*

fun RBuilder.Root() {
    child(RootComponent)
}

private val RootComponent = functionalComponent<RProps> {

    val (authenticated, setAuthenticated) = useState(Kita.auth.isAuthenticated())

    if (authenticated) {
        Search()
    } else {
        Auth("Authentication", setAuthenticated)
    }

}
