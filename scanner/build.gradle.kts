plugins {
    kotlin("jvm")
}

kotlin {
    explicitApi()
}

dependencies {

    testImplementation(libs.junit)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotlin.test.junit5)

    api(projects.metricsFile)
}
