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
import kotlinx.css.Position
import kotlinx.css.height
import kotlinx.css.left
import kotlinx.css.pct
import kotlinx.css.position
import kotlinx.css.properties.transform
import kotlinx.css.properties.translate
import kotlinx.css.top
import kotlinx.css.width
import kotlinx.html.FlowOrMetaDataContent
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.progress
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
                styleLink("https://res.cloudinary.com/finnhvman/raw/upload/matter/matter-0.2.2.min.css")
                styleCss {
                    rule("html, body, #kita") {
                        width = 100.pct
                        height = 100.pct
                    }
                }
                application
                    .listJarFileResources("assets/js")
                    .sorted()
                    .forEach {
                        script("text/javascript", it) {
                            async = true
                        }
                    }
            }
            body {
                div {
                    id = "kita"
                    progress("matter-progress-circular") {
                        styleCss {
                            rule("progress") {
                                position = Position.absolute
                                left = 50.pct
                                top = 50.pct
                                transform {
                                    translate((-50).pct, (-50).pct)
                                }
                            }
                        }
                    }
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
