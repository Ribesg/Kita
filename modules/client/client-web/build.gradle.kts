@file:Suppress("ConstantConditionIf")

import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

repositories {
    maven(url = "https://kotlin.bintray.com/kotlin-js-wrappers/")
}

plugins {
    kotlin("js")
    id("kotlin-dce-js")
}

kotlin {

    target {
        browser {
            webpackTask {

                // Basic Webpack task configuration
                archiveFileName = Build.webClientJsFileName
                report = true
                saveEvaluatedConfigFile = true
                sourceMaps = Build.isSnapshot

                // Bind Webpack production mode to Build.isSnapshot using the webpack.config.d directory
                if (!Build.isSnapshot) {
                    val prodConfigFile = file("webpack.config.d/production.js")
                    doFirst {
                        prodConfigFile.run {
                            parentFile.mkdirs()
                            writeText("config.mode = 'production';")
                        }
                    }
                    doLast {
                        prodConfigFile.delete()
                    }
                }

                // DCE specific configuration
                val compileKotlinJs by tasks.getting(Kotlin2JsCompile::class)
                val runDceKotlin by tasks.getting(KotlinJsDce::class)
                dependsOn(runDceKotlin)
                runDceKotlin.run {
                    dceOptions {
                        devMode = Build.isSnapshot
                    }
                }
                doFirst {
                    copy {
                        from(runDceKotlin.destinationDir)
                        into("${compileKotlinJs.destinationDir}")
                        include("${project.name}*")
                    }
                    copy {
                        from(runDceKotlin.destinationDir)
                        into("${compileKotlinJs.destinationDir}/node_modules")
                        exclude("${project.name}*")
                    }
                }

            }
        }
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

        implementation("io.ktor:ktor-client-js:${Versions.ktor}")
        implementation("io.ktor:ktor-client-json-js:${Versions.ktor}")
        implementation("io.ktor:ktor-client-serialization-js:${Versions.ktor}")

    }

}
