@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components

import fr.ribesg.kita.client.common.Kita
import fr.ribesg.kita.client.web.AuthAction.SetAuthenticated
import fr.ribesg.kita.client.web.components.common.Button
import fr.ribesg.kita.client.web.components.common.Input
import fr.ribesg.kita.client.web.components.common.Link
import fr.ribesg.kita.client.web.components.util.createComponentScope
import fr.ribesg.kita.client.web.useAuthStore
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.html.InputType
import react.*
import react.dom.div
import react.dom.h3
import react.router.dom.redirect
import kotlin.browser.window

fun RBuilder.auth() =
    child(AuthComponent)

private enum class AuthAction(
    val displayName: String,
    val switchText: String,
    val execute: suspend (login: String, password: String) -> Unit
) {

    LOGIN(
        "Login",
        "No account? Register!",
        Kita.auth::login
    ),

    REGISTER(
        "Register",
        "Already have an account?",
        Kita.auth::register
    ),

    ;

    val other: AuthAction
        get() = values().single { it != this }

}

private val AuthComponent = functionalComponent<RProps> {

    val scope = createComponentScope()

    val (action, setAction) = useState(AuthAction.LOGIN)
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

    val onSwitchLinkClicked: () -> Unit = {
        setAction(action.other)
    }
    div("columns is-centered is-vcentered") {
        div("column is-one-quarter") {
            div("box") {
                div("field is-grouped is-grouped-centered") {
                    div("control") {
                        h3("title") {
                            +"Authentication"
                        }
                    }
                }
                Input(
                    icon = "fas fa-user",
                    isDisabled = isLoading,
                    onInputTextChanged = setLoginInput,
                    label = "Login",
                    value = loginInput
                )
                Input(
                    icon = "fas fa-lock",
                    isDisabled = isLoading,
                    onInputTextChanged = setPasswordInput,
                    label = "Password",
                    value = passwordInput,
                    type = InputType.password
                )
                if (action == AuthAction.REGISTER) {
                    Input(
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
                        Button(
                            isDisabled = !isActionButtonEnabled() || isLoading,
                            isPrimary = true,
                            onClick = onActionButtonClicked,
                            text = action.displayName
                        )
                    }
                }
                div("field is-grouped is-grouped-centered") {
                    div("control") {
                        Link(
                            text = action.switchText,
                            onClick = onSwitchLinkClicked
                        )
                    }
                }
            }
        }
    }

}
