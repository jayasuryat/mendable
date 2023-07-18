plugins {
    id("com.diffplug.spotless") version "6.3.0"
    id("org.jetbrains.kotlin.jvm") version "1.8.0" apply false
}

apply {
    from("buildScripts/spotless_pre_commit_hook.gradle")
}

allprojects.forEach { project ->
    project.apply {
        from("${rootProject.rootDir}/buildScripts/spotless.gradle")
    }
}
