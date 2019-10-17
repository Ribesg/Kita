package fr.ribesg.kita.client.web.components.util

import kotlinx.coroutines.*
import react.useEffectWithCleanup

val componentScope: CoroutineScope
    get() = (MainScope() + Job()).apply {
        useEffectWithCleanup(emptyList()) { { cancel() } }
    }
