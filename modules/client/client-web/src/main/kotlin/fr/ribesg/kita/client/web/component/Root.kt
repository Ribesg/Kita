@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.component

import fr.ribesg.kita.client.web.authStore
import fr.ribesg.kita.client.web.component.search.search
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
                route("/", exact = true) {
                    home()
                }
                route("/login", exact = true) {
                    auth(false)
                }
                route("/register", exact = true) {
                    auth(true)
                }
                privateRoute("/search", exact = true) {
                    search()
                }
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
        attrs.path = path.split('/').filter(String::isNotEmpty).toTypedArray()
        attrs.exact = exact
        attrs.strict = strict
        attrs.render = { render() }
    }

private val PrivateRouteComponent = functionalComponent<RouteProps<RProps>> { props ->

    val auth = useAuthState()

    if (auth.isAuthenticated) {
        route(
            exact = props.exact,
            strict = props.strict,
            render = props.render,
            path = props.path,
        )
    } else {
        redirect("*", "/login")
    }

}
