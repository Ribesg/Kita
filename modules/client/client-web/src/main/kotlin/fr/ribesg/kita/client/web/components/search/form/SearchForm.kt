package fr.ribesg.kita.client.web.components.search.form

import fr.ribesg.kita.client.web.components.ui.Button
import fr.ribesg.kita.client.web.components.ui.Input
import kotlinx.css.Display.flex
import kotlinx.css.FlexDirection.row
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
            display = flex
            flexDirection = row
        }
        Input(
            enabled = isInputEnabled,
            onEnterKeyPressed = onSearchTriggered,
            onInputTextChanged = onInputChanged
        )
        Button(
            text = "Search",
            enabled = isButtonEnabled,
            onClick = onSearchTriggered
        )
    }
}
