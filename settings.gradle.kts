// https://github.com/gradle/gradle/issues/16608
rootProject.name = "Mendable-project"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

include(":mendable-app")
include(":scanner")
include(":parser")
include(":metrics-file")
include(":mendable")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
