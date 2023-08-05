import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.binary.compatibility.validator)
    alias(libs.plugins.spotless)
}

apply {
    from("buildScripts/spotless_pre_commit_hook.gradle")
}

allprojects.forEach { project ->
    project.apply {
        from("${rootProject.rootDir}/buildScripts/spotless.gradle")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
