@file:Suppress("FunctionName")

package fr.ribesg.kita.client.web.components.search

import fr.ribesg.kita.client.common.Kita
import fr.ribesg.kita.client.web.components.ui.Input
import fr.ribesg.kita.client.web.components.util.createComponentScope
import fr.ribesg.kita.common.model.SearchResponse
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.css.em
import kotlinx.css.width
import react.*
import react.dom.div
import styled.css
import styled.styledImg

fun RBuilder.Search() =
    child(SearchComponent)

private val SearchComponent = functionalComponent<RProps> {

    val scope = createComponentScope()

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

    div {
        Input(
            onInputTextChanged = setQuery,
            placeholder = "Query",
            value = query
        )
        response?.movies?.forEach { movie ->
            movie.posterUrl?.let { src ->
                styledImg(src = src) {
                    css {
                        width = 7.5.em
                    }
                }
            }
        }
    }

}
