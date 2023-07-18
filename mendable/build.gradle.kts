plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    explicitApi()
}

dependencies {

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.skyscreamer:jsonassert:1.5.1")
    testImplementation("com.google.code.gson:gson:2.10.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation(project(":scanner"))

    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.8.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")

    api(project(":metrics-file"))
    implementation(project(":scanner"))
    implementation(project(":parser"))
}
