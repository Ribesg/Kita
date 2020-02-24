@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components.auth

import fr.ribesg.kita.client.common.Kita
import fr.ribesg.kita.client.web.components.ui.Button
import fr.ribesg.kita.client.web.components.ui.Input
import fr.ribesg.kita.client.web.components.ui.Link
import fr.ribesg.kita.client.web.components.util.createComponentScope
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
import react.useState
import styled.css
import styled.styledDiv
import kotlin.browser.window

fun RBuilder.Auth(
    title: String,
    authenticationListener: (isAuthenticated: Boolean) -> Unit
) =
    child(AuthComponent) {
        attrs.title = title
        attrs.authenticationListener = authenticationListener
    }

private interface AuthProps : RProps {
    var title: String
    var authenticationListener: (isAuthenticated: Boolean) -> Unit
}

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

private val AuthComponent = functionalComponent<AuthProps> { props ->

    val scope = createComponentScope()

    val (action, setAction) = useState(AuthAction.LOGIN)
    val (loginInput, setLoginInput) = useState("")
    val (passwordInput, setPasswordInput) = useState("")
    val (confirmPasswordInput, setConfirmPasswordInput) = useState("")
    val (isLoading, setLoading) = useState(false)

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
            props.authenticationListener(true)
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
            +props.title
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
