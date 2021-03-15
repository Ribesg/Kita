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
            api("org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.serialization}")
        }

        getByName("jvmMain").dependencies {
            api(kotlin("stdlib-jdk8"))
        }

        getByName("jsMain").dependencies {
            api(kotlin("stdlib-js"))
        }

    }

}
