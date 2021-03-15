package fr.ribesg.kita.server.route

import fr.ribesg.kita.server.MetaProperties
import fr.ribesg.kita.server.util.listJarFileResources
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import kotlinx.css.*
import kotlinx.css.properties.transform
import kotlinx.css.properties.translate
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.koin.ktor.ext.inject

fun Routing.index() {

    val meta by inject<MetaProperties>()

    val indexHtml by lazy {
        StringBuilder()
            .appendHTML()
            .html {
                indexHead(application, meta)
                indexBody()
            }
            .toString()
    }

    get("{...}") {
        call.respondText(indexHtml, ContentType.Text.Html)
    }

}

private fun HTML.indexHead(application: Application, meta: MetaProperties) {
    head {
        title("Kita ${meta.version}")
        meta(charset = "utf-8")
        meta(name = "viewport", content = "width=device-width, initial-scale=1")
        styleLink("https://cdn.jsdelivr.net/npm/bulma@0.8.0/css/bulma.min.css")
        style {
            unsafe {
                //language=CSS
                +"""
                    @keyframes onAutoFillStart {}
                    
                    @keyframes onAutoFillEnd {}
                    
                    input:-webkit-autofill {
                        animation-name: onAutoFillStart;
                    }
                    
                    input:not(:-webkit-autofill) {
                        animation-name: onAutoFillEnd;
                    }
                """.trimIndent()
            }
        }
        styleCss {
            rule("html, body, #kita") {
                width = 100.pct
                height = 100.pct
            }
        }
        ascript("https://use.fontawesome.com/releases/v5.12.1/js/all.js")
        application
            .listJarFileResources("assets/js")
            .filter { it.endsWith(".js") }
            .sorted()
            .forEach(::ascript)
    }
}

private fun HTML.indexBody() {
    body("has-background-dark has-text-light") {
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

private fun FlowOrMetaDataContent.styleCss(builder: CSSBuilder.() -> Unit) =
    style(ContentType.Text.CSS.toString()) {
        unsafe { +CSSBuilder().apply(builder).toString() }
    }

private fun FlowOrPhrasingOrMetaDataContent.ascript(src: String) =
    script(src = src) { async = true }
