plugins {
    alias(libs.plugins.module.android.library)
}

android {
    namespace = "com.gb.opaltest.features.home.domain"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.features.gems.domain)
    implementation(projects.features.referral.domain)
    implementation(projects.features.rewards.domain)
}