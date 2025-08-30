plugins {
    alias(libs.plugins.module.android.library)
    alias(libs.plugins.module.android.presentation)
}

android {
    namespace = "com.gb.opaltest.features.home.presentation"
}

dependencies {
    implementation(libs.bundles.coil)

    implementation(projects.core.common)
    implementation(projects.core.design)
    implementation(projects.core.translations)
    implementation(projects.features.gems.domain)
    implementation(projects.features.gems.presentation)
    implementation(projects.features.home.domain)
    implementation(projects.features.referral.domain)
    implementation(projects.features.rewards.domain)
}
