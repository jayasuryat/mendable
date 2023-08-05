plugins {
    kotlin("jvm")
}

kotlin {
    explicitApi()
}

dependencies {

    testImplementation(libs.kotlin.test.junit5)
    testImplementation(libs.junit)
    testImplementation(libs.jsonassert)
    testImplementation(libs.gson)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(projects.scanner)

    implementation(libs.kotlinx.html.jvm)
    implementation(libs.gson)
    implementation(libs.kotlinx.coroutines.core)

    api(projects.metricsFile)
    implementation(projects.scanner)
    implementation(projects.parser)
}
