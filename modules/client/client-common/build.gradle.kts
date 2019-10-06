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
            api(project(":modules:common"))
            api(kotlin("stdlib-common"))
        }

        getByName("jvmMain").dependencies {
            api(kotlin("stdlib-jdk8"))
        }

        getByName("jsMain").dependencies {
            api(kotlin("stdlib-js"))
        }

    }

}
