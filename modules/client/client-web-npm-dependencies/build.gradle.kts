plugins {
    kotlin("js")
}

kotlin {

    target {
        nodejs()
    }

    sourceSets["main"].dependencies {

        implementation(npm("core-js"))
        implementation(npm("redux", Versions.redux))
        implementation(npm("react", Versions.react))
        implementation(npm("react-redux", Versions.reactRedux))
        implementation(npm("react-dom", Versions.react))
        implementation(npm("react-router-dom", Versions.reactRouter))
        implementation(npm("inline-style-prefixer", Versions.inlineStylePrefixer))
        implementation(npm("styled-components", Versions.styledComponents))

        // TODO Workaround for https://github.com/Kotlin/kotlinx-io/issues/57
        api(npm("text-encoding"))

    }

}
