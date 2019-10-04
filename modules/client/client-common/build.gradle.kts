plugins {
    kotlin("multiplatform")
}

kotlin {

    jvm()

    js {
        browser()
    }

    sourceSets {

        getByName("commonMain").dependencies {
            implementation(project(":modules:common"))
            implementation(kotlin("stdlib-common"))
        }

        getByName("jvmMain").dependencies {
            implementation(kotlin("stdlib-jdk8"))
        }

        getByName("jsMain").dependencies {
            implementation(kotlin("stdlib-js"))
        }

    }

}
