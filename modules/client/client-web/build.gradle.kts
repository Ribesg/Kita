@file:Suppress("ConstantConditionIf")

import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

plugins {
    kotlin("js")
}

kotlin {

    target {
        browser {
            webpackTask {
                outputFileName = Build.name + ".[contentHash].js"
            }
        }
        useCommonJs()
    }

    sourceSets["main"].dependencies {

        api(project(":modules:common"))
        api(project(":modules:client:client-common"))

        api(kotlin("stdlib-js"))

        api("org.jetbrains:kotlin-extensions:${Versions.kotlinExtensions}")
        api("org.jetbrains:kotlin-redux:${Versions.reduxWrapper}")
        api("org.jetbrains:kotlin-react:${Versions.reactWrapper}")
        api("org.jetbrains:kotlin-react-redux:${Versions.reactReduxWrapper}")
        api("org.jetbrains:kotlin-react-dom:${Versions.reactWrapper}")
        api("org.jetbrains:kotlin-react-router-dom:${Versions.reactRouterWrapper}")
        api("org.jetbrains:kotlin-css:${Versions.kotlinCss}")
        api("org.jetbrains:kotlin-css-js:${Versions.kotlinCssJs}")
        api("org.jetbrains:kotlin-styled:${Versions.kotlinStyled}")

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

tasks.withType<KotlinWebpack> {
    doFirst {
        destinationDirectory?.listFiles()?.forEach { it.delete() }
    }
}
