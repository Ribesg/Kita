@file:Suppress("MayBeConstant", "MemberVisibilityCanBePrivate", "ConstantConditionIf")

import org.gradle.api.JavaVersion

object Versions {

    // General
    val java = JavaVersion.VERSION_1_8
    val kotlin = "1.4.31"
    val gradle = "6.8.3"
    val versions = "0.38.0"

    // Common
    val coroutines = "1.4.2-native-mt"
    val serialization = "1.1.0"

    // JVM
    val bouncyCastle = "1.68"
    val jackson = "2.12.2"
    val koin = "2.2.2"
    val ktor = "1.5.2"
    val logback = "1.2.3"
    val sqldelight = "1.4.4"

    // JS
    val abortController = "3.0.0"
    val coreJs = "3.9.1"
    val redux = "4.0.5"
    val react = "17.0.1"
    val reactRedux = "7.2.2"
    val reactRouter = "5.2.0"
    val inlineStylePrefixer = "6.0.0"
    val styledComponents = "5.2.1"
    val textEncoding = "0.7.0"
    val jetbrainsWrapper = "pre.149"
    val kotlinCss = "1.0.0-$jetbrainsWrapper-kotlin-$kotlin"
    val kotlinCssJs = "1.0.0-$jetbrainsWrapper-kotlin-$kotlin"
    val kotlinExtensions = "1.0.1-$jetbrainsWrapper-kotlin-$kotlin"
    val kotlinStyled = "5.2.1-$jetbrainsWrapper-kotlin-$kotlin"
    val reduxWrapper = "$redux-$jetbrainsWrapper-kotlin-$kotlin"
    val reactWrapper = "$react-$jetbrainsWrapper-kotlin-$kotlin"
    val reactReduxWrapper = "$reactRedux-$jetbrainsWrapper-kotlin-$kotlin"
    val reactRouterWrapper = "$reactRouter-$jetbrainsWrapper-kotlin-$kotlin"

    // Android
    val androidPlugin = "4.0.1"
    val androidMinSdk = 23
    val androidSdk = 30

}
