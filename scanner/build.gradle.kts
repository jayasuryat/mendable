plugins {
    kotlin("jvm")
}

kotlin {
    explicitApi()
}

dependencies {

    testImplementation(libs.junit)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.assertions.json)

    api(projects.metricsFile)
}
