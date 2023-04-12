import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    application
}

val mainCliClassName = "com.jayasuryat.mendable.MainKt"

repositories {
    mavenCentral()
}

dependencies {

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("junit:junit:4.13.2")

    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.5")
    implementation("com.google.code.gson:gson:2.10.1")
}

application {
    mainClass.set(mainCliClassName)
}

kotlin {
    explicitApi = ExplicitApiMode.Strict
}

tasks.named<Test>("test") {
    useJUnitPlatform()
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
