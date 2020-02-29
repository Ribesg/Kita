@file:Suppress("FunctionName", "unused")

package fr.ribesg.kita.client.web

import fr.ribesg.kita.client.common.Kita
import fr.ribesg.kita.client.web.AuthAction.SetAuthenticated
import react.RBuilder
import react.RDispatch
import react.createContext
import react.useContext
import react.useReducer

data class AuthState(
    val isAuthenticated: Boolean
)

sealed class AuthAction {
    class SetAuthenticated(val value: Boolean) : AuthAction()
}

private val initialState by lazy {
    AuthState(
        isAuthenticated = Kita.auth.isAuthenticated()
    )
}

private val AuthReducer: (state: AuthState, action: AuthAction) -> AuthState =
    { state, action ->
        when (action) {
            is SetAuthenticated -> {
                state.copy(isAuthenticated = action.value)
            }
        }
    }

private val AuthStateContext = createContext<AuthState>()
private val AuthDispatchContext = createContext<RDispatch<AuthAction>>()

fun RBuilder.authStore(children: RBuilder.() -> Unit) {
    val (state, dispatch) = useReducer(AuthReducer, initialState)
    AuthStateContext.Provider(state) {
        AuthDispatchContext.Provider(dispatch) {
            children()
        }
    }
}

fun useAuthState() =
    useContext(AuthStateContext)

fun useAuthDispatch() =
    useContext(AuthDispatchContext)

fun useAuthStore() =
    useAuthState() to useAuthDispatch()
