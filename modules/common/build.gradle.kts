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
            api(kotlin("stdlib-common"))
            api("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:${Versions.serialization}")
        }

        getByName("jvmMain").dependencies {
            api(kotlin("stdlib-jdk8"))
            api("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.serialization}")
        }

        getByName("jsMain").dependencies {
            api(kotlin("stdlib-js"))
            api("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:${Versions.serialization}")
        }

    }

}
