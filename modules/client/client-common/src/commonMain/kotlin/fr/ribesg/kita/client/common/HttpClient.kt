package fr.ribesg.kita.client.common

import fr.ribesg.kita.client.common.auth.KitaAuthProvider
import io.ktor.client.HttpClient
import io.ktor.client.features.auth.Auth
import io.ktor.client.features.json.Json
import io.ktor.client.features.json.serializer.KotlinxSerializer

internal val http = HttpClient {
    Auth {
        providers += KitaAuthProvider
    }
    Json {
        serializer = KotlinxSerializer()
    }
}

internal expect val baseUrl: String
