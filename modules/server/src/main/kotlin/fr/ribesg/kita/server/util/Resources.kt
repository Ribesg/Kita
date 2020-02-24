package fr.ribesg.kita.server.util

import io.ktor.application.Application
import java.util.jar.JarFile

private val fileInJarUrlRegex = "^jar:file:.+!.+$".toRegex()

fun Application.listJarFileResources(inFolderWithPath: String): List<String> {
    val loader = environment.classLoader
    val urls = loader.getResources(inFolderWithPath)?.toList() ?: emptyList()
    urls.forEach { url ->
        val urlString = url.toString()
        if (!urlString.matches(fileInJarUrlRegex)) return@forEach
        val separatorIndex = urlString.indexOf('!', startIndex = 9)
        val jarFilePath = urlString.substring(9, separatorIndex)
        val folderInJarPath = urlString
            .substring(separatorIndex + 1)
            .removePrefix("/")
            .removeSuffix("/")
            .plus("/")
        return JarFile(jarFilePath)
            .entries()
            .asSequence()
            .filter {
                !it.isDirectory && it.name.startsWith(folderInJarPath)
            }
            .map { "/" + it.name }
            .toList()
    }
    return emptyList()
}
