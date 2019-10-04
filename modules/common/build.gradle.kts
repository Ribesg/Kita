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
            implementation(kotlin("stdlib-common"))
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:${Versions.serialization}")
        }

        getByName("jvmMain").dependencies {
            implementation(kotlin("stdlib-jdk8"))
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.serialization}")
        }

        getByName("jsMain").dependencies {
            implementation(kotlin("stdlib-js"))
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:${Versions.serialization}")
        }

    }

}
