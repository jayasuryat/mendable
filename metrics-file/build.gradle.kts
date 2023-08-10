@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    kotlin("jvm")
    alias(libs.plugins.poko)
}

kotlin {
    explicitApi()
}
