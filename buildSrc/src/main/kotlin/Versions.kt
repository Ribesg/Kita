@file:Suppress("MayBeConstant", "MemberVisibilityCanBePrivate", "ConstantConditionIf")

import org.gradle.api.JavaVersion

object Versions {

    // General
    val java = JavaVersion.VERSION_1_8
    val kotlin = "1.3.61"
    val gradle = "6.1.1"
    val versions = "0.27.0"

    // Common
    val coroutines = "1.3.3"
    val serialization = "0.14.0"

    // JVM
    val bouncyCastle = "1.64"
    val jackson = "2.10.2"
    val koin = "2.0.1"
    val ktor = "1.3.1"
    val logback = "1.2.3"
    val sqldelight = "1.2.2"

    // JS
    val redux = "4.0.0"
    val react = "16.9.0"
    val reactRedux = "5.0.7"
    val reactRouter = "4.3.1"
    val inlineStylePrefixer = "5.1.0"
    val styledComponents = "4.4.0"
    val jetbrainsWrapper = "pre.91"
    val kotlinCss = "1.0.0-$jetbrainsWrapper-kotlin-$kotlin"
    val kotlinCssJs = "1.0.0-$jetbrainsWrapper-kotlin-$kotlin"
    val kotlinExtensions = "1.0.1-$jetbrainsWrapper-kotlin-$kotlin"
    val kotlinStyled = "1.0.0-$jetbrainsWrapper-kotlin-$kotlin"
    val reduxWrapper = "$redux-$jetbrainsWrapper-kotlin-$kotlin"
    val reactWrapper = "$react-$jetbrainsWrapper-kotlin-$kotlin"
    val reactReduxWrapper = "$reactRedux-$jetbrainsWrapper-kotlin-$kotlin"
    val reactRouterWrapper = "$reactRouter-$jetbrainsWrapper-kotlin-$kotlin"

    // Android
    val androidPlugin = "3.5.3"
    val androidMinSdk = 23
    val androidSdk = 29

}
