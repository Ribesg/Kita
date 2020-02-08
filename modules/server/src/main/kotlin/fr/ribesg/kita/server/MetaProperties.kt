package fr.ribesg.kita.server

import java.util.*

interface MetaProperties {
    val contact: String
    val name: String
    val uniqueBuildNumber: String
    val version: String
    val webClientJsFileName: String
}

class MetaPropertiesImpl : MetaProperties {

    override val contact: String
        get() = properties.getProperty("contact")

    override val name: String
        get() = properties.getProperty("name")

    override val uniqueBuildNumber: String
        get() = properties.getProperty("uniqueBuildNumber")

    override val version: String
        get() = properties.getProperty("version")

    override val webClientJsFileName: String
        get() = properties.getProperty("webClientJsFileName")

    private val properties = Properties().apply {
        load(
            MetaProperties::class.java.classLoader.getResourceAsStream("metadata.properties")
                ?: throw IllegalStateException("metadata.properties not found")
        )
    }

}
