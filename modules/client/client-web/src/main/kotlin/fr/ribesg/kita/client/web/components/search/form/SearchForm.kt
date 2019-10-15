package fr.ribesg.kita.client.web.components.search.form

import fr.ribesg.kita.client.web.components.ui.button
import fr.ribesg.kita.client.web.components.ui.input
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.display
import kotlinx.css.flexDirection
import react.RBuilder
import styled.css
import styled.styledDiv

fun RBuilder.searchForm(
    isInputEnabled: Boolean,
    isButtonEnabled: Boolean,
    onInputChanged: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    styledDiv {
        css {
            display = Display.flex
            flexDirection = FlexDirection.row
        }
        input(
            enabled = isInputEnabled,
            onEnterKeyPressed = onSearchTriggered,
            onInputTextChanged = onInputChanged
        )
        button(
            text = "Search",
            enabled = isButtonEnabled,
            onButtonClicked = onSearchTriggered
        )
    }
}
