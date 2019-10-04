import org.gradle.jvm.tasks.Jar

plugins {
    kotlin("jvm")
    kotlin("plugin.serialization") version Versions.kotlin
    id("com.squareup.sqldelight") version Versions.sqldelight
}

dependencies {

    implementation(project(":modules:common"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.serialization}")

    implementation("ch.qos.logback:logback-classic:${Versions.logback}")

    implementation("org.koin:koin-core:${Versions.koin}")
    implementation("org.koin:koin-ktor:${Versions.koin}")
    implementation("org.koin:koin-logger-slf4j:${Versions.koin}")

    implementation("io.ktor:ktor-client-cio:${Versions.ktor}")
    implementation("io.ktor:ktor-client-json:${Versions.ktor}")
    implementation("io.ktor:ktor-client-logging-jvm:${Versions.ktor}")
    implementation("io.ktor:ktor-client-serialization-jvm:${Versions.ktor}")
    implementation("io.ktor:ktor-html-builder:${Versions.ktor}")
    implementation("io.ktor:ktor-serialization:${Versions.ktor}")
    implementation("io.ktor:ktor-server-cio:${Versions.ktor}")

    implementation("com.squareup.sqldelight:sqlite-driver:${Versions.sqldelight}")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${Versions.jackson}")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:${Versions.jackson}")

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
    from(webClientProject.buildDir.resolve("distributions")) {
        include("*.js")
    }
    into(buildDir.resolve("resources").resolve("main").resolve("assets"))
}

tasks.getByName<Jar>("jar") {
    dependsOn("copyWebClient", "createProperties")
    archiveFileName.set("${Build.name}.jar")
    manifest.attributes(
        "Main-Class" to "${project.group}.${project.name}.MainKt"
    )
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}
