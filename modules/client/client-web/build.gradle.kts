@file:Suppress("ConstantConditionIf")

import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

repositories {
    maven(url = "https://kotlin.bintray.com/kotlin-js-wrappers/")
}

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
    }

    sourceSets["main"].dependencies {

        api(project(":modules:common"))
        api(project(":modules:client:client-common"))
        api(project(":modules:client:client-web-npm-dependencies"))

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

    }

}

tasks.withType<KotlinWebpack> {
    doFirst {
        destinationDirectory?.listFiles()?.forEach { it.delete() }
    }
}
