@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.component

import fr.ribesg.kita.client.common.Kita
import fr.ribesg.kita.client.web.AuthAction.SetAuthenticated
import fr.ribesg.kita.client.web.component.common.button
import fr.ribesg.kita.client.web.component.common.input
import fr.ribesg.kita.client.web.component.common.routeLink
import fr.ribesg.kita.client.web.component.util.createComponentScope
import fr.ribesg.kita.client.web.style.KitaStyle
import fr.ribesg.kita.client.web.useAuthStore
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.html.InputType
import react.*
import react.dom.div
import react.dom.h3
import react.router.dom.redirect
import styled.css
import styled.styledDiv
import kotlinx.browser.window

fun RBuilder.auth(isRegister: Boolean) =
    child(AuthComponent) {
        attrs.isRegister = isRegister
    }

private interface AuthProps : RProps {
    var isRegister: Boolean
}

private enum class AuthAction(
    val displayName: String,
    val switchText: String,
    val switchDestination: String,
    val execute: suspend (login: String, password: String) -> Unit
) {

    LOGIN(
        displayName = "Login",
        switchText = "No account? Register!",
        switchDestination = "/register",
        execute = Kita.auth::login
    ),

    REGISTER(
        displayName = "Register",
        switchText = "Already have an account?",
        switchDestination = "/login",
        execute = Kita.auth::register
    ),

}

private val AuthComponent = functionalComponent<AuthProps> { props ->

    val scope = createComponentScope()

    val action = if (props.isRegister) AuthAction.REGISTER else AuthAction.LOGIN
    val (loginInput, setLoginInput) = useState("")
    val (passwordInput, setPasswordInput) = useState("")
    val (confirmPasswordInput, setConfirmPasswordInput) = useState("")
    val (isLoading, setLoading) = useState(false)

    val (auth, authDispatch) = useAuthStore()

    if (auth.isAuthenticated) {
        redirect("*", "/")
        return@functionalComponent
    }

    val isActionButtonEnabled = {
        loginInput.isNotBlank() && passwordInput.isNotEmpty()
    }

    val onActionButtonClicked: () -> Unit = {
        scope.launch(CoroutineExceptionHandler { _, error ->
            setLoading(false)
            console.error("Failed to ${action.displayName}", error)
            window.alert("Failed to ${action.displayName}: $error")
        }) {
            setLoading(true)
            action.execute(loginInput, passwordInput)
            console.info("${action.displayName} successful")
            authDispatch(SetAuthenticated(true))
        }
    }

    styledDiv {
        css { +KitaStyle.center }
        div("box") {
            div("field is-grouped is-grouped-centered") {
                div("control") {
                    h3("title") {
                        +"Authentication"
                    }
                }
            }
            input(
                icon = "fas fa-user",
                isDisabled = isLoading,
                onInputTextChanged = setLoginInput,
                label = "Login",
                value = loginInput
            )
            input(
                icon = "fas fa-lock",
                isDisabled = isLoading,
                onInputTextChanged = setPasswordInput,
                label = "Password",
                value = passwordInput,
                type = InputType.password
            )
            if (action == AuthAction.REGISTER) {
                input(
                    icon = "fas fa-lock",
                    isDisabled = isLoading,
                    onInputTextChanged = setConfirmPasswordInput,
                    label = "Password confirmation",
                    value = confirmPasswordInput,
                    type = InputType.password
                )
            }
            div("field is-grouped is-grouped-centered") {
                div("control") {
                    button(
                        isDisabled = !isActionButtonEnabled() || isLoading,
                        isPrimary = true,
                        onClick = onActionButtonClicked,
                        text = action.displayName
                    )
                }
            }
            div("field is-grouped is-grouped-centered") {
                div("control") {
                    routeLink(action.switchText, action.switchDestination)
                }
            }
        }
    }

}
