plugins {
    kotlin("jvm")
}

kotlin {
    explicitApi()
}

dependencies {

    testImplementation(libs.kotlin.reflect)
    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.junit)
    testImplementation(libs.jsonassert)
    testImplementation(projects.scanner)

    implementation(libs.gson)
    api(projects.metricsFile)
}
