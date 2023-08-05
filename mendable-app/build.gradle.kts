import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    kotlin("jvm")
    application
}

val mainCliClassName = "com.jayasuryat.mendable.app.MainKt"

dependencies {

    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.junit)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.jsonassert)

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
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
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
