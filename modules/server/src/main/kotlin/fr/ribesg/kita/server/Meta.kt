package fr.ribesg.kita.server

import java.util.*

object Meta {

    val contact: String
        get() = properties.getProperty("contact")

    val name: String
        get() = properties.getProperty("name")

    val uniqueBuildNumber: String
        get() = properties.getProperty("uniqueBuildNumber")

    val version: String
        get() = properties.getProperty("version")

    val webClientJsFileName: String
        get() = properties.getProperty("webClientJsFileName")

    private val properties = Properties().apply {
        load(
            javaClass.getResourceAsStream("/metadata.properties")
                ?: throw IllegalStateException("metadata.properties not found")
        )
    }

}
