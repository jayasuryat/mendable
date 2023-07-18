plugins {
    id("com.diffplug.spotless") version "6.3.0"
    id("org.jetbrains.kotlin.jvm") version "1.8.0" apply false
    id("org.jetbrains.kotlinx.binary-compatibility-validator") version "0.13.2"
}

apply {
    from("buildScripts/spotless_pre_commit_hook.gradle")
}

allprojects.forEach { project ->
    project.apply {
        from("${rootProject.rootDir}/buildScripts/spotless.gradle")
    }
}
