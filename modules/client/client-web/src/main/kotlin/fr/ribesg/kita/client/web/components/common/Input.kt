@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components.common

import kotlinx.html.INPUT
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onKeyDownFunction
import org.w3c.dom.events.KeyboardEvent
import react.RBuilder
import react.dom.input
import react.dom.label
import react.dom.span

fun RBuilder.Input(
    enabled: Boolean = true,
    label: String = "Input",
    onEnterKeyPressed: (() -> Unit)? = null,
    onInputTextChanged: ((String) -> Unit)? = null,
    type: InputType? = null,
    value: String = ""
) {
    label("matter-textfield-standard") {
        input {
            attrs.placeholder = " "
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
            type?.let { attrs.type = it }
            attrs.value = value
        }
        span {
            +label
        }
    }
}
