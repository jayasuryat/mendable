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
    testImplementation(project(":scanner"))

    api(project(":metrics-file"))
}
