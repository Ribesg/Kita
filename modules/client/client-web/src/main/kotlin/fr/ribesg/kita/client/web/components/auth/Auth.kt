@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components.auth

import fr.ribesg.kita.client.common.Apis
import fr.ribesg.kita.client.web.components.ui.Button
import fr.ribesg.kita.client.web.components.ui.Input
import fr.ribesg.kita.client.web.components.ui.Link
import fr.ribesg.kita.client.web.components.util.createComponentScope
import fr.ribesg.kita.common.model.AuthTokens
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.css.Display.flex
import kotlinx.css.FlexDirection.column
import kotlinx.css.display
import kotlinx.css.flexDirection
import kotlinx.html.InputType
import react.*
import react.dom.h3
import styled.css
import styled.styledDiv
import kotlin.browser.window

interface AuthProps : RProps {
    var title: String
}

fun RBuilder.Auth(title: String) =
    child(AuthComponent) {
        attrs.title = title
    }

private enum class AuthAction(
    val displayName: String,
    val switchText: String,
    val execute: suspend (login: String, password: String) -> AuthTokens
) {

    LOGIN(
        "Login",
        "No account? Register!",
        Apis.auth::login
    ),

    REGISTER(
        "Register",
        "Already have an account?",
        Apis.auth::register
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
            val tokens = action.execute(loginInput, passwordInput)
            console.info("accessToken=${tokens.accessToken}")
            console.info("refreshToken=${tokens.refreshToken}")
            setLoading(false)
        }
    }

    val onSwitchLinkClicked: () -> Unit = {
        setAction(action.other)
    }

    styledDiv {
        css {
            display = flex
            flexDirection = column
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
