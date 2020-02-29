import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
    }
}

allprojects {

    group = Build.group
    version = Build.version

    repositories {
        mavenCentral()
        jcenter()
        maven(url = "https://kotlin.bintray.com/kotlin-js-wrappers/")
    }

}

plugins {
    idea
    id("com.github.ben-manes.versions") version Versions.versions
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

tasks.withType<Wrapper> {
    gradleVersion = Versions.gradle
    distributionType = Wrapper.DistributionType.ALL
}

tasks.withType<DependencyUpdatesTask> {

    fun isNonStable(version: String): Boolean {
        val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
        val regex = "^[0-9,.v-]+$".toRegex()
        val isStable = stableKeyword || regex.matches(version)
        return isStable.not()
    }

    rejectVersionIf {
        isNonStable(candidate.version) && !isNonStable(currentVersion)
    }

}
