@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    kotlin("jvm")
    alias(libs.plugins.poko)
}

val publishVersion: String by rootProject.ext
val publishGroupId: String by rootProject.ext
ext {
    this["PUBLISH_GROUP_ID"] = publishGroupId
    this["PUBLISH_VERSION"] = publishVersion
    this["PUBLISH_ARTIFACT_ID"] = "metrics-file"
}

apply {
    from("${rootProject.projectDir}/buildScripts/publish/publish-module.gradle")
}

kotlin {
    explicitApi()
}

dependencies {
    compileOnly(libs.kotlin.std.lib)
}
