plugins {
    alias(libs.plugins.module.android.library)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "com.gb.opaltest.features.referral.domain"
}

dependencies {
    implementation(libs.kotlinx.serialization)

    implementation(projects.core.common)
}
