plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    implementation("com.google.guava:guava:31.1-jre")
}

application {
    mainClass.set("com.jayasuryat.mendable.AppKt")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
