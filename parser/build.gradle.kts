plugins {
    kotlin("jvm")
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
}
