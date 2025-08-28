import com.gb.opaltest.build_logic.implementation

plugins {
    alias(libs.plugins.module.android.library)
}

android {
    namespace = "com.gp.opaltest.core.navigation.api"
}

dependencies {
    implementation(libs.androidx.navigation)
}