plugins {
    alias(libs.plugins.module.android.library)
    alias(libs.plugins.module.android.presentation)
}

android {
    namespace = "com.gb.opaltest.features.splash_screen.presentation"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.design)
    implementation(projects.core.translations)
    implementation(projects.features.splashScreen.navigation)
}
