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
    testImplementation(libs.kotlinx.coroutines.test)

    implementation(libs.kotlinx.html.jvm)
    implementation(libs.gson)
    implementation(libs.kotlinx.coroutines.core)

    api(projects.metricsFile)
    implementation(projects.scanner)
    implementation(projects.parser)
}
