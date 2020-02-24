import org.gradle.jvm.tasks.Jar

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version Versions.kotlin
    id("com.squareup.sqldelight") version Versions.sqldelight
}

dependencies {

    api(project(":modules:common"))

    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
    api("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.serialization}")

    api("ch.qos.logback:logback-classic:${Versions.logback}")

    api("org.koin:koin-core:${Versions.koin}")
    api("org.koin:koin-ktor:${Versions.koin}")
    api("org.koin:koin-logger-slf4j:${Versions.koin}")

    api("io.ktor:ktor-auth-jwt:${Versions.ktor}")
    api("io.ktor:ktor-client-cio:${Versions.ktor}")
    api("io.ktor:ktor-client-json:${Versions.ktor}")
    api("io.ktor:ktor-client-logging-jvm:${Versions.ktor}")
    api("io.ktor:ktor-client-serialization-jvm:${Versions.ktor}")
    api("io.ktor:ktor-html-builder:${Versions.ktor}")
    api("io.ktor:ktor-locations:${Versions.ktor}")
    api("io.ktor:ktor-serialization:${Versions.ktor}")
    api("io.ktor:ktor-server-cio:${Versions.ktor}")

    api("com.squareup.sqldelight:sqlite-driver:${Versions.sqldelight}")

    api("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}")
    api("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${Versions.jackson}")

    api("org.bouncycastle:bcprov-jdk15on:${Versions.bouncyCastle}")

}

sqldelight {
    database("Database") {
        packageName = "${project.group}.${project.name}"
    }
}

tasks.create("createProperties") {
    doLast {
        mkdir("$buildDir/resources/main")
        File("$buildDir/resources/main/metadata.properties").writeText(
            """
            contact=${Build.contact}
            name=${Build.name}
            uniqueBuildNumber=${Build.id}
            version=${Build.version}
            webClientJsFileName=${Build.webClientJsFileName}
            """.trimIndent()
        )
    }
}

tasks.create<Copy>("copyWebClient") {
    val webClientProjectPath = ":modules:client:client-web"
    val webClientProject = project(webClientProjectPath)
    dependsOn("$webClientProjectPath:build")

    val destinationDir = buildDir
        .resolve("resources")
        .resolve("main")
        .resolve("assets")

    doFirst {
        destinationDir
            .listFiles { _, name ->
                name.startsWith(Build.name) && name.endsWith(".js")
            }
            ?.forEach { file ->
                file.delete()
            }
    }

    from(webClientProject.buildDir.resolve("distributions")) {
        include(Build.webClientJsFileName)
    }

    into(destinationDir)
}

tasks.getByName<Jar>("jar") {
    dependsOn("copyWebClient", "createProperties")
    archiveFileName.set("${Build.name}.jar")
    manifest.attributes(
        "Main-Class" to "${project.group}.${project.name}.MainKt"
    )
    from({
        configurations
            .runtimeClasspath.get()
            .filter { it.name.endsWith("jar") }
            .map {
                zipTree(it).matching {
                    exclude("**/*.SF", "**/*.DSA", "**/*.RSA")
                }
            }
    })
}
