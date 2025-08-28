plugins {
    alias(libs.plugins.module.android.library)
}

android {
    namespace = "com.gb.opaltest.core.common"
}

dependencies {
    implementation(libs.bundles.datastore)
}