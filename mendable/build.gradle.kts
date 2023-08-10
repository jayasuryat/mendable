import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    kotlin("jvm")
    alias(libs.plugins.poko)
}

kotlin {
    explicitApi()
}

tasks.named<Test>("test") {
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = setOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
    }
}

dependencies {

    testImplementation(libs.junit)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.assertions.json)
    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.kotlinx.html.jvm)
    implementation(libs.gson)
    implementation(libs.kotlinx.coroutines.core)

    api(projects.metricsFile)
    implementation(projects.scanner)
    implementation(projects.parser)
}
