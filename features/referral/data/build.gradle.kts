plugins {
    alias(libs.plugins.module.android.library)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.gb.opaltest.features.referral.data"
}

dependencies {
    implementation(libs.kotlinx.serialization)
    implementation(libs.bundles.datastore)

    implementation(projects.core.common)
    implementation(projects.features.referral.domain)
}
