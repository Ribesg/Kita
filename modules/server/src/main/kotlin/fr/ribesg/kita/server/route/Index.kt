package fr.ribesg.kita.server.route

import fr.ribesg.kita.server.MetaProperties
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.routing.Routing
import io.ktor.routing.get
import kotlinx.html.*
import org.koin.ktor.ext.inject

fun Routing.index() {
    val meta by inject<MetaProperties>()
    get("{...}") {
        call.respondHtml {
            head {
                title("Kita ${meta.version}")
                style {
                    unsafe {
                        +"""
                    html, body, #kita {
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        height: 100%;
                    }
                    
                    .loader {
                        border: 8px solid transparent;
                        border-top-color: green;
                        border-radius: 50%;
                        width: 60px;
                        height: 60px;
                        margin: auto;
                        animation: spin 1s linear infinite;
                    }
                    
                    @keyframes spin {
                        0% { transform: rotate(0deg); }
                        100% { transform: rotate(360deg); }
                    }
                    """.trimIndent()
                    }
                }
            }
            body {
                div {
                    id = "kita"
                    div("loader")
                }
                script("text/javascript", "/assets/${meta.webClientJsFileName}", {})
            }
        }
    }
}
