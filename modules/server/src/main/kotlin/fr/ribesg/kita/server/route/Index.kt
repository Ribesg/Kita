package fr.ribesg.kita.server.route

import fr.ribesg.kita.server.MetaProperties
import fr.ribesg.kita.server.util.listJarFileResources
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.routing.Routing
import io.ktor.routing.get
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
                style {
                    unsafe {
                        +"""
                            html, body, #kita {
                                width: 100%;
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
                application.listJarFileResources("assets/js").forEach {
                    script("text/javascript", it, {})
                }
            }
        }
    }
}
