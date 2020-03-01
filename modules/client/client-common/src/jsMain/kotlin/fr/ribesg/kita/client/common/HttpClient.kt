package fr.ribesg.kita.client.common

import kotlin.browser.window

internal actual val baseUrl: String
    get() = window.location.origin
