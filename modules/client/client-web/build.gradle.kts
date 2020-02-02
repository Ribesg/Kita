@file:Suppress("ConstantConditionIf")

repositories {
    maven(url = "https://kotlin.bintray.com/kotlin-js-wrappers/")
}

plugins {
    kotlin("js")
}

kotlin {

    target {
        browser()
    }

    sourceSets["main"].dependencies {

        implementation(project(":modules:common"))
        implementation(project(":modules:client:client-common"))
        implementation(project(":modules:client:client-web-npm-dependencies"))

        implementation(kotlin("stdlib-js"))

        implementation("org.jetbrains:kotlin-extensions:${Versions.kotlinExtensions}")
        implementation("org.jetbrains:kotlin-redux:${Versions.reduxWrapper}")
        implementation("org.jetbrains:kotlin-react:${Versions.reactWrapper}")
        implementation("org.jetbrains:kotlin-react-redux:${Versions.reactReduxWrapper}")
        implementation("org.jetbrains:kotlin-react-dom:${Versions.reactWrapper}")
        implementation("org.jetbrains:kotlin-react-router-dom:${Versions.reactRouterWrapper}")
        implementation("org.jetbrains:kotlin-css:${Versions.kotlinCss}")
        implementation("org.jetbrains:kotlin-css-js:${Versions.kotlinCssJs}")
        implementation("org.jetbrains:kotlin-styled:${Versions.kotlinStyled}")

    }

}
