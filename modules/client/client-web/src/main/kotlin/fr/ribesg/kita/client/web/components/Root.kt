@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components

import fr.ribesg.kita.client.web.authStore
import fr.ribesg.kita.client.web.useAuthState
import react.RBuilder
import react.RProps
import react.ReactElement
import react.child
import react.functionalComponent
import react.router.dom.RouteProps
import react.router.dom.browserRouter
import react.router.dom.redirect
import react.router.dom.route
import react.router.dom.switch

fun RBuilder.root() {
    child(RootComponent)
}

private val RootComponent = functionalComponent<RProps> {
    authStore {
        browserRouter {
            switch {
                privateRoute("/", exact = true) {
                    content()
                }
                route("/login") {
                    auth()
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
