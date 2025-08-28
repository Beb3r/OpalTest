plugins {
    alias(libs.plugins.module.android.library)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.gb.opaltest.features.splash_screen.navigation"
}

dependencies {
    implementation(libs.kotlinx.serialization)
    implementation(projects.core.navigation.api)
}
