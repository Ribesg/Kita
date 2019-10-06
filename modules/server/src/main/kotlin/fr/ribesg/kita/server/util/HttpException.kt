package fr.ribesg.kita.server.util

import io.ktor.http.HttpStatusCode

data class HttpException(
    val status: HttpStatusCode,
    override val message: String? = null,
    override val cause: Throwable? = null
) : Exception(message, cause)
