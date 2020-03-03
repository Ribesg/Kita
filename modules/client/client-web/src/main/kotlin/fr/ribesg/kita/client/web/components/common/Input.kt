@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components.common

import kotlinx.html.INPUT
import kotlinx.html.InputType
import kotlinx.html.classes
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onKeyDownFunction
import org.w3c.dom.events.KeyboardEvent
import react.RBuilder
import react.dom.*

fun RBuilder.Input(
    icon: String? = null,
    isDisabled: Boolean = false,
    label: String? = null,
    onEnterKeyPressed: (() -> Unit)? = null,
    onInputTextChanged: ((String) -> Unit)? = null,
    type: InputType? = null,
    value: String = ""
) {
    div("field") {
        label?.let {
            label("label") { +it }
        }
        div("control") {
            input(classes = "input") {
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
                attrs.disabled = isDisabled
                attrs.type = type ?: InputType.text
                attrs.value = value
            }
            if (icon != null) {
                attrs.classes += "has-icons-left"
                span("icon is-small is-left") {
                    i(icon) {}
                }
            }
        }
    }
}
