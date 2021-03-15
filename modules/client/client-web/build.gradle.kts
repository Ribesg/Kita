@file:Suppress("ConstantConditionIf")

import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.Mode.*

plugins {
    kotlin("js")
}

kotlin {

    js {
        browser {
            webpackTask {
                mode = if (Build.isSnapshot) DEVELOPMENT else PRODUCTION
                outputFileName = Build.name + ".[contentHash].js"
                sourceMaps = true
                doFirst {
                    destinationDirectory.listFiles()?.forEach { it.delete() }
                }
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

        api(npm("core-js", Versions.coreJs))
        api(npm("redux", Versions.redux))
        api(npm("react", Versions.react))
        api(npm("react-redux", Versions.reactRedux))
        api(npm("react-dom", Versions.react))
        api(npm("react-router-dom", Versions.reactRouter))
        api(npm("inline-style-prefixer", Versions.inlineStylePrefixer))
        api(npm("styled-components", Versions.styledComponents))

        api(npm("abort-controller", Versions.abortController))
        // TODO Workaround for https://github.com/Kotlin/kotlinx-io/issues/57
        api(npm("text-encoding", Versions.textEncoding))

    }

}
