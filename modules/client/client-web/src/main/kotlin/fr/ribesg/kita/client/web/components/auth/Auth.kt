package fr.ribesg.kita.client.web.components.auth

import fr.ribesg.kita.client.common.Apis
import fr.ribesg.kita.client.web.components.auth.AuthContainer.State
import fr.ribesg.kita.client.web.components.ui.button
import fr.ribesg.kita.client.web.components.ui.input
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.display
import kotlinx.css.flexDirection
import kotlinx.html.InputType
import react.*
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
        setState { loginInput = text }

    private fun onPasswordChanged(text: String) =
        setState { passwordInput = text }

    private fun onLoginButtonClicked() {
        GlobalScope.launch(CoroutineExceptionHandler { _, error ->
            console.error("Failed to call login", error)
        }) {
            val tokens = Apis.auth.login(state.loginInput, state.passwordInput)
            window.alert("Tokens: $tokens")
        }
    }

    class State(
        var loginInput: String,
        var passwordInput: String,
        var isSignUpMode: Boolean
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
