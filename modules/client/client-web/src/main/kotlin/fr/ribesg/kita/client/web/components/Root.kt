@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components

import fr.ribesg.kita.client.web.authStore
import fr.ribesg.kita.client.web.components.search.search
import fr.ribesg.kita.client.web.useAuthState
import react.*
import react.router.dom.*

fun RBuilder.root() {
    child(RootComponent)
}

private val RootComponent = functionalComponent<RProps> {
    authStore {
        browserRouter {
            switch {
                privateRoute("/search", exact = true) {
                    search()
                }
                route("/login") {
                    auth()
                }
                redirect("/", "/search")
            }
        }
    }
}

private fun RBuilder.privateRoute(
    path: String,
    exact: Boolean = false,
    strict: Boolean = false,
    render: () -> ReactElement?
) =
    child(PrivateRouteComponent) {
        attrs.path = path
        attrs.exact = exact
        attrs.strict = strict
        attrs.render = { render() }
    }

private val PrivateRouteComponent = functionalComponent<RouteProps<RProps>> { props ->

    val auth = useAuthState()

    if (auth.isAuthenticated) {
        route(props.path, props.exact, props.strict, props.render)
    } else {
        redirect("*", "/login")
    }

}
