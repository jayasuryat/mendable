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
    implementation("com.google.guava:guava:31.1-jre")
}

application {
    mainClass.set(mainCliClassName)
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
