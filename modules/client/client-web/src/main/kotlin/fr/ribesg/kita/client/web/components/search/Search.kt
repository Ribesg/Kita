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
import react.*
import react.dom.p
import styled.css
import styled.styledDiv

fun RBuilder.Search() =
    child(SearchComponent)

private val SearchComponent = functionalComponent<RProps> {

    val scope = createComponentScope()

    val (query, setQuery) = useState("")
    val (isLoading, setLoading) = useState(false)
    val (response, setResponse) = useState<SearchResponse?>(null)

    useEffectWithCleanup(listOf(query)) {
        if (query.length < 3) return@useEffectWithCleanup {}
        setResponse(null)
        setLoading(true)
        val job = scope.launch(CoroutineExceptionHandler { _, e ->
            if (e is CancellationException) return@CoroutineExceptionHandler
            console.error("Error in search", e)
        }) {
            setResponse(Kita.search.search(query))
            setLoading(false)
        }
        return@useEffectWithCleanup { job.cancel("Cancelled by cleanup") }
    }

    styledDiv {
        css {

        }
        Input(
            onInputTextChanged = setQuery,
            placeholder = "Query",
            value = query
        )
        if (isLoading) {
            p {
                +"Chargement…"
            }
        } else {
            response?.movies?.forEach {
                p {
                    +it.title
                }
            }
        }
    }
}
