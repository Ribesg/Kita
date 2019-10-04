@file:Suppress("ConstantConditionIf", "EXPERIMENTAL_API_USAGE", "MemberVisibilityCanBePrivate")

import java.util.*

object Build {

    private const val major = 0
    private const val minor = 0
    private const val fixes = 1
    const val isSnapshot = true
    private val snapshot = if (isSnapshot) "-SNAPSHOT" else ""
    val versionCode = if (isSnapshot) 1 else major * 10000 + minor * 100 + fixes
    val version = "$major.$minor.$fixes$snapshot"

    const val contact = "ribesg@gmail.com"
    const val name = "kita"
    const val group = "fr.ribesg.$name"
    val id = UUID.randomUUID().toString().replace("-", "")
    val webClientJsFileName = "$name-$id.js"

}
