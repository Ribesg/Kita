@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.component.search

import fr.ribesg.kita.client.common.Kita
import fr.ribesg.kita.client.web.AuthAction
import fr.ribesg.kita.client.web.component.common.button
import fr.ribesg.kita.client.web.component.common.input
import fr.ribesg.kita.client.web.component.util.createComponentScope
import fr.ribesg.kita.client.web.useAuthDispatch
import fr.ribesg.kita.common.model.SearchResponse
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.css.Align
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.alignItems
import kotlinx.css.display
import kotlinx.css.em
import kotlinx.css.flexDirection
import kotlinx.css.flexGrow
import kotlinx.css.margin
import kotlinx.css.padding
import kotlinx.html.InputType
import react.*
import styled.css
import styled.styledDiv

fun RBuilder.search() =
    child(SearchComponent)

private val SearchComponent = functionalComponent<RProps> {

    val scope = createComponentScope()

    val authDispatch = useAuthDispatch()
    val (query, setQuery) = useState("")
    val (response, setResponse) = useState<SearchResponse?>(null)

    useEffectWithCleanup(listOf(query)) {
        if (query.length < 3) {
            setResponse(null)
            return@useEffectWithCleanup {}
        }
        val job = scope.launch(CoroutineExceptionHandler { _, e ->
            if (e is CancellationException) return@CoroutineExceptionHandler
            console.error("Error in search", e)
        }) {
            setResponse(Kita.search.search(query))
        }
        return@useEffectWithCleanup { job.cancel("Cancelled by cleanup") }
    }

    styledDiv {
        css {
            display = Display.flex
            flexDirection = FlexDirection.column
            padding = .25.em.value
            children {
                margin = .25.em.value
            }
        }
        styledDiv {
            css {
                display = Display.flex
                flexDirection = FlexDirection.row
                alignItems = Align.center
                padding = .25.em.value
                children {
                    firstChild {
                        flexGrow = 1.0
                    }
                    margin = .25.em.value
                }
            }
            input(
                icon = "fas fa-search",
                onInputTextChanged = setQuery,
                label = "Query",
                type = InputType.search,
                value = query
            )
            button("Logout") {
                Kita.auth.logout()
                authDispatch(AuthAction.SetAuthenticated(false))
            }
        }
        response?.movies?.filter { it.posterUrl != null }?.forEach { movie ->
            searchResult(movie)
        }
    }

}
