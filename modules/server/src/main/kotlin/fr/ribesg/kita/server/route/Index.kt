package fr.ribesg.kita.server.route

import fr.ribesg.kita.server.MetaProperties
import fr.ribesg.kita.server.util.listJarFileResources
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.routing.Routing
import io.ktor.routing.get
import kotlinx.css.CSSBuilder
import kotlinx.css.height
import kotlinx.css.pct
import kotlinx.css.width
import kotlinx.html.FlowOrMetaDataContent
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.script
import kotlinx.html.style
import kotlinx.html.styleLink
import kotlinx.html.title
import kotlinx.html.unsafe
import org.koin.ktor.ext.inject

fun Routing.index() {
    val meta by inject<MetaProperties>()
    get("{...}") {
        call.respondHtml {
            head {
                title("Kita ${meta.version}")
                styleLink("https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.1/normalize.min.css")
                styleCss {
                    rule("html, body, #kita") {
                        width = 100.pct
                        height = 100.pct
                    }
                }
            }
            body {
                div {
                    id = "kita"
                }
                application.listJarFileResources("assets/js").forEach {
                    script("text/javascript", it) {}
                }
            }
        }
    }
}

private fun FlowOrMetaDataContent.styleCss(builder: CSSBuilder.() -> Unit) {
    style(ContentType.Text.CSS.toString()) {
        unsafe { +CSSBuilder().apply(builder).toString() }
    }
}
