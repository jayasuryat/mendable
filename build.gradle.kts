import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.binary.compatibility.validator)
    alias(libs.plugins.spotless)
    alias(libs.plugins.poko) apply false
    alias(libs.plugins.gradle.nexus.publish)
}

apply {
    from("buildScripts/spotless_pre_commit_hook.gradle")
    from("buildScripts/publish/publish-root.gradle")
}

ext.set("publishGroupId", "com.jayasuryat.mendable")

allprojects.forEach { project ->
    project.apply {
        from("${rootProject.rootDir}/buildScripts/spotless.gradle")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.register("publishAllPublicationsToSonatypeRepository")
private val publishAll = tasks.findByName("publishAllPublicationsToSonatypeRepository")
if (publishAll != null) {
    allprojects {
        tasks.whenTaskAdded {
            if (this is PublishToMavenRepository) {
                publishAll.dependsOn(this)
            }
        }
    }
}
