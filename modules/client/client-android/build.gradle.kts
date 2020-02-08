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

    api(project(":modules:common"))
    api(project(":modules:client:client-common"))

    api(kotlin("stdlib-jdk7"))

}
