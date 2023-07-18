import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    id("org.jetbrains.kotlin.jvm")
    application
}

val mainCliClassName = "com.jayasuryat.mendable.app.MainKt"

dependencies {

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.kotest:kotest-assertions-core:5.6.2")
    testImplementation("org.skyscreamer:jsonassert:1.5.1")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.5")

    implementation(project(":mendable"))
}

application {
    mainClass.set(mainCliClassName)
}

kotlin {
    explicitApi = ExplicitApiMode.Strict
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
