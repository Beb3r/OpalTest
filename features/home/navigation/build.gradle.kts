plugins {
    alias(libs.plugins.module.android.library)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.gb.opaltest.features.home.navigation"
}

dependencies {
    implementation(libs.kotlinx.serialization)
    implementation(projects.core.navigation.api)
}