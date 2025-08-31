import com.gb.opaltest.build_logic.implementation

plugins {
    alias(libs.plugins.module.android.library)
    alias(libs.plugins.module.android.presentation)
}

android {
    namespace = "com.gb.opaltest.core.design"
}

dependencies {
    implementation(libs.androidx.palette)
    implementation(libs.liquidGlass)
    implementation(libs.bundles.coil)

    implementation(projects.core.translations)
}