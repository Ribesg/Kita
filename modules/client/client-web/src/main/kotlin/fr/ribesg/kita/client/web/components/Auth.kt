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
import kotlinx.css.Align
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.JustifyContent
import kotlinx.css.alignItems
import kotlinx.css.display
import kotlinx.css.em
import kotlinx.css.flexDirection
import kotlinx.css.height
import kotlinx.css.justifyContent
import kotlinx.css.margin
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.html.InputType
import react.RBuilder
import react.RProps
import react.child
import react.dom.h3
import react.functionalComponent
import react.router.dom.redirect
import react.useState
import styled.css
import styled.styledDiv
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

    styledDiv {
        css {
            display = Display.flex
            flexDirection = FlexDirection.column
            alignItems = Align.center
            justifyContent = JustifyContent.center
            height = 100.pct
            padding(.5.em)
            children {
                margin(.5.em)
            }
        }
        h3 {
            +"Authentication"
        }
        Input(
            enabled = !isLoading,
            onInputTextChanged = setLoginInput,
            placeholder = "Login",
            value = loginInput
        )
        Input(
            enabled = !isLoading,
            onInputTextChanged = setPasswordInput,
            placeholder = "Password",
            value = passwordInput,
            type = InputType.password
        )
        if (action == AuthAction.REGISTER) {
            Input(
                enabled = !isLoading,
                onInputTextChanged = setConfirmPasswordInput,
                placeholder = "Password confirmation",
                value = confirmPasswordInput,
                type = InputType.password
            )
        }
        Button(
            enabled = isActionButtonEnabled() && !isLoading,
            onClick = onActionButtonClicked,
            text = action.displayName
        )
        Link(
            text = action.switchText,
            onClick = onSwitchLinkClicked
        )
    }

}
