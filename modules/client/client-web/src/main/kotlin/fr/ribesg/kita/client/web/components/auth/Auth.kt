package fr.ribesg.kita.client.web.components.auth

import fr.ribesg.kita.client.web.components.auth.AuthContainer.State
import fr.ribesg.kita.client.web.components.ui.button
import fr.ribesg.kita.client.web.components.ui.input
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.display
import kotlinx.css.flexDirection
import kotlinx.html.InputType
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import styled.css
import styled.styledDiv
import kotlin.browser.window

fun RBuilder.auth() {
    child(AuthContainer::class) {}
}

private class AuthContainer : RComponent<RProps, State>() {

    private val isLoginButtonEnabled: Boolean
        get() = state.run { loginInput.isNotEmpty() && passwordInput.isNotEmpty() }

    init {
        state = State(
            loginInput = "",
            passwordInput = "",
            isSignUpMode = false
        )
    }

    override fun RBuilder.render() = authView(
        loginInput = state.loginInput,
        onLoginChanged = ::onLoginChanged,
        passwordInput = state.passwordInput,
        onPasswordChanged = ::onPasswordChanged,
        loginButtonText = if (state.isSignUpMode) "Signup" else "Login",
        isLoginButtonEnabled = isLoginButtonEnabled,
        onLoginButtonClicked = ::onLoginButtonClicked
    )

    private fun onLoginChanged(text: String) =
        setState(state.copy(loginInput = text))

    private fun onPasswordChanged(text: String) =
        setState(state.copy(passwordInput = text))

    private fun onLoginButtonClicked() {
        val (login, password) = state
        window.alert("login=$login, password=$password")
    }

    data class State(
        val loginInput: String,
        val passwordInput: String,
        val isSignUpMode: Boolean
    ) : RState

}

private fun RBuilder.authView(
    loginInput: String,
    onLoginChanged: (String) -> Unit,
    passwordInput: String,
    onPasswordChanged: (String) -> Unit,
    loginButtonText: String,
    isLoginButtonEnabled: Boolean,
    onLoginButtonClicked: () -> Unit
) {
    styledDiv {
        css {
            display = Display.flex
            flexDirection = FlexDirection.column
        }
        input(
            onInputTextChanged = onLoginChanged,
            value = loginInput
        )
        input(
            onInputTextChanged = onPasswordChanged,
            value = passwordInput,
            type = InputType.password
        )
        button(
            enabled = isLoginButtonEnabled,
            onButtonClicked = onLoginButtonClicked,
            text = loginButtonText
        )
    }
}
