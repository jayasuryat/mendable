import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    kotlin("jvm")
    alias(libs.plugins.poko)
}

val publishVersion: String by rootProject.ext
val publishGroupId: String by rootProject.ext
ext {
    this["PUBLISH_GROUP_ID"] = publishGroupId
    this["PUBLISH_VERSION"] = publishVersion
    this["PUBLISH_ARTIFACT_ID"] = "parser"
}

apply {
    from("${rootProject.projectDir}/buildScripts/publish/publish-module.gradle")
}

kotlin {
    explicitApi()
}

dependencies {

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.reflect)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.assertions.json)
    testImplementation(projects.scanner)

    implementation(libs.gson)
    api(projects.metricsFile)
    compileOnly(libs.kotlin.std.lib)
}

tasks.named<Test>("test") {
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
    }
}
