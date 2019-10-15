package fr.ribesg.kita.client.web.components.auth

import fr.ribesg.kita.client.web.components.ui.button
import fr.ribesg.kita.client.web.components.ui.input
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.display
import kotlinx.css.flexDirection
import kotlinx.html.InputType
import react.RProps
import react.RState
import react.functionalComponent
import react.useState
import styled.css
import styled.styledDiv
import kotlin.browser.window

interface AuthProps : RProps {
  var loginButtonText: String
}

private data class State(
  val loginInput: String,
  val passwordInput: String,
  val isSignUpMode: Boolean
) : RState

val AuthContainer = functionalComponent<AuthProps> { props ->
  val (state, setState) = useState(
    State(
      loginInput = "",
      passwordInput = "",
      isSignUpMode = false
    )
  )

  val onLoginChanged = { text: String ->
    setState(state.copy(loginInput = text))
  }

  val onPasswordChanged = { text: String ->
    setState(state.copy(passwordInput = text))
  }

  val onLoginButtonClicked = {
    val (login, password) = state
    window.alert("login=$login, password=$password")
  }

  val isLoginButtonEnabled = state.run { loginInput.isNotEmpty() && passwordInput.isNotEmpty() }

  styledDiv {
    css {
      display = Display.flex
      flexDirection = FlexDirection.column
    }
    input(
      onInputTextChanged = onLoginChanged,
      value = state.loginInput
    )
    input(
      onInputTextChanged = onPasswordChanged,
      value = state.passwordInput,
      type = InputType.password
    )
    button(
      enabled = isLoginButtonEnabled,
      onButtonClicked = onLoginButtonClicked,
      text = props.loginButtonText
    )
  }
}

