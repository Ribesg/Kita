package fr.ribesg.kita.client.common

import kotlinx.browser.window

internal actual val baseUrl: String
    get() = window.location.origin
