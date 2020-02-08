plugins {
    kotlin("js")
}

kotlin {

    target {
        nodejs()
    }

    sourceSets["main"].dependencies {

        api(npm("core-js"))
        api(npm("redux", Versions.redux))
        api(npm("react", Versions.react))
        api(npm("react-redux", Versions.reactRedux))
        api(npm("react-dom", Versions.react))
        api(npm("react-router-dom", Versions.reactRouter))
        api(npm("inline-style-prefixer", Versions.inlineStylePrefixer))
        api(npm("styled-components", Versions.styledComponents))

        api(npm("abort-controller"))
        // TODO Workaround for https://github.com/Kotlin/kotlinx-io/issues/57
        api(npm("text-encoding"))

    }

}
