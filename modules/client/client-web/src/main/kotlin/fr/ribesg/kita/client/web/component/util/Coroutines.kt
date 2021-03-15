package fr.ribesg.kita.client.web.component.util

import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.plus
import react.useEffectWithCleanup

fun createComponentScope() =
    (MainScope() + Job()).apply {
        useEffectWithCleanup(emptyList()) { { cancel() } }
    }
