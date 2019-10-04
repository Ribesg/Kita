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

// TODO Meh, maybe we should not start things inside Gradle
tasks.create<JavaExec>("run") {
    dependsOn(":modules:client:client-web:build")
    dependsOn(":modules:server:build")
    classpath(project(":modules:server").buildDir.resolve("libs").resolve("${Build.name}.jar"))
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
