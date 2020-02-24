@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components.ui

import kotlinx.css.margin
import kotlinx.css.px
import kotlinx.html.INPUT
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onKeyDownFunction
import org.w3c.dom.events.KeyboardEvent
import react.RBuilder
import styled.css
import styled.styledInput

fun RBuilder.Input(
    enabled: Boolean = true,
    onEnterKeyPressed: (() -> Unit)? = null,
    onInputTextChanged: ((String) -> Unit)? = null,
    placeholder: String? = null,
    type: InputType? = null,
    value: String = ""
) {
    styledInput {
        attrs.disabled = !enabled
        onEnterKeyPressed?.let { callback ->
            attrs.onKeyDownFunction = {
                val event = it.asDynamic().nativeEvent as KeyboardEvent
                if (event.key == "Enter") callback()
            }
        }
        onInputTextChanged?.let { callback ->
            attrs.onChangeFunction = {
                callback(it.target.unsafeCast<INPUT>().value)
            }
        }
        placeholder?.let { attrs.placeholder = it }
        type?.let { attrs.type = it }
        attrs.value = value
    }
}
