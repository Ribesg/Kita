package fr.ribesg.kita.server.route

import fr.ribesg.kita.server.MetaProperties
import fr.ribesg.kita.server.util.listJarFileResources
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.routing.Routing
import io.ktor.routing.get
import kotlinx.css.*
import kotlinx.css.properties.transform
import kotlinx.css.properties.translate
import kotlinx.html.*
import org.koin.ktor.ext.inject

fun Routing.index() {
    val meta by inject<MetaProperties>()
    get("{...}") {
        call.respondHtml {
            classes += "has-background-dark"
            head {
                title("Kita ${meta.version}")
                meta(charset = "utf-8")
                meta(name = "viewport", content = "width=device-width, initial-scale=1")
                styleLink("https://cdn.jsdelivr.net/npm/bulma@0.8.0/css/bulma.min.css")
                styleCss {
                    rule("html, body, #kita") {
                        width = 100.pct
                        height = 100.pct
                    }
                }
                script(src = "https://use.fontawesome.com/releases/v5.12.1/js/all.js") { async = true }
                application
                    .listJarFileResources("assets/js")
                    .sorted()
                    .forEach {
                        // script(src = it) { async = true }
                    }
            }
            body("has-text-light") {
                div {
                    id = "kita"
                    span("icon is-large") {
                        id = "loader"
                        styleCss {
                            rule("#loader") {
                                position = Position.absolute
                                left = 50.pct
                                top = 50.pct
                                transform {
                                    translate((-50).pct, (-50).pct)
                                }
                            }
                        }
                        i("fas fa-3x fa-fan fa-spin")
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
