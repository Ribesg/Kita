rootProject.name = Build.name

include(
    *findProjects(rootProject.projectDir.resolve("modules"))
        .map {
            it
                .relativeTo(rootProject.projectDir)
                .invariantSeparatorsPath
                .replace('/', ':')
        }
        .toTypedArray()
)

pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        jcenter()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.android.application") {
                useModule("com.android.tools.build:gradle:${requested.version}")
            }
            if (requested.id.id == "com.squareup.sqldelight") {
                useModule("com.squareup.sqldelight:gradle-plugin:${requested.version}")
            }
        }
    }
}

/**
 * Find projects under the provided directory.
 *
 * Any directory containing a directory named 'src' is returned as a project.
 */
fun findProjects(root: File): List<File> =
    if (root.resolve("src").isDirectory) {
        listOf(root)
    } else {
        root.listFiles { dir, _ -> dir.isDirectory }?.flatMap(::findProjects) ?: emptyList()
    }
