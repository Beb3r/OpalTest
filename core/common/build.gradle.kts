plugins {
    alias(libs.plugins.module.android.library)
    alias(libs.plugins.module.android.presentation)
}

android {
    namespace = "com.gb.opaltest.core.common"
}

dependencies {
    implementation(libs.bundles.datastore)
}