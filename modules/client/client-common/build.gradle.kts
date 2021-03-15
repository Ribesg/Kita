plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version Versions.kotlin
}

kotlin {

    jvm()

    js {
        browser()
    }

    sourceSets {

        getByName("commonMain").dependencies {
            api(project(":modules:common"))
            api(kotlin("stdlib-common"))
            api("org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.serialization}")
            api("io.ktor:ktor-client-auth:${Versions.ktor}")
            api("io.ktor:ktor-client-core:${Versions.ktor}")
            api("io.ktor:ktor-client-json:${Versions.ktor}")
            api("io.ktor:ktor-client-serialization:${Versions.ktor}")
        }

        getByName("jvmMain").dependencies {
            api(kotlin("stdlib-jdk8"))
            api("io.ktor:ktor-client-auth-jvm:${Versions.ktor}")
            api("io.ktor:ktor-client-okhttp:${Versions.ktor}")
            api("io.ktor:ktor-client-json-jvm:${Versions.ktor}")
            api("io.ktor:ktor-client-serialization-jvm:${Versions.ktor}")
        }

        getByName("jsMain").dependencies {
            api(kotlin("stdlib-js"))
            api("io.ktor:ktor-client-auth-js:${Versions.ktor}")
            api("io.ktor:ktor-client-js:${Versions.ktor}")
            api("io.ktor:ktor-client-json-js:${Versions.ktor}")
            api("io.ktor:ktor-client-serialization-js:${Versions.ktor}")
        }

    }

}
