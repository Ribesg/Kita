repositories {
    google()
}

plugins {
    id("com.android.application") version Versions.androidPlugin
}

android {

    setCompileSdkVersion(Versions.androidSdk)

    defaultConfig {
        minSdkVersion(Versions.androidMinSdk)
        targetSdkVersion(Versions.androidSdk)
    }

    compileOptions {
        sourceCompatibility = Versions.java
        targetCompatibility = Versions.java
    }

    lintOptions {
        isAbortOnError = false
    }

    packagingOptions {
        exclude("META-INF/*.kotlin_module")
    }

}

dependencies {

    implementation(project(":modules:common"))
    implementation(project(":modules:client:client-common"))

    implementation(kotlin("stdlib-jdk7"))

}

// Temporary workaround for https://youtrack.jetbrains.com/issue/KT-27170
configurations.create("compileClasspath")
tasks.named("lint") { enabled = false }
