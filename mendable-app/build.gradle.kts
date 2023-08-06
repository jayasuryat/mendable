import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    kotlin("jvm")
    application
}

val mainCliClassName = "com.jayasuryat.mendable.app.MainKt"

dependencies {

    testImplementation(libs.junit)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.assertions.json)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.cli)

    implementation(projects.mendable)
}

application {
    mainClass.set(mainCliClassName)
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

tasks.withType<Jar> {

    manifest {
        attributes["Main-Class"] = mainCliClassName
    }

    val dependencies = configurations
        .runtimeClasspath
        .get()
        .map(::zipTree)

    from(dependencies)

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
