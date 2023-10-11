import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.binary.compatibility.validator)
    alias(libs.plugins.spotless)
    alias(libs.plugins.poko) apply false
    alias(libs.plugins.dokka)
    alias(libs.plugins.gradle.nexus.publish)
}

apply {
    from("buildScripts/publish/publish-root.gradle")
}

ext.set("publishGroupId", "com.jayasuryat.mendable")

allprojects.forEach { project ->
    project.apply {
        from("${rootProject.rootDir}/buildScripts/spotless.gradle")
    }
}

subprojects {

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions {
            apiVersion = "1.7"
            languageVersion = "1.7"
        }
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }
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

project("mendable-app").afterEvaluate {

    fun findJarTaskInSubproject(projectName: String): Task? {
        return project.rootProject.subprojects
            .find { project -> project.name == projectName }
            ?.tasks?.named("jar")
            ?.get()
    }

    val metricsFile = findJarTaskInSubproject("metrics-file") ?: return@afterEvaluate
    val scanner = findJarTaskInSubproject("scanner") ?: return@afterEvaluate
    val parser = findJarTaskInSubproject("parser") ?: return@afterEvaluate
    val mendable = findJarTaskInSubproject("mendable") ?: return@afterEvaluate
    val mendableApp = findJarTaskInSubproject("mendable-app") ?: return@afterEvaluate

    mendableApp.dependsOn(metricsFile, scanner, parser, mendable)
}
