plugins {
    alias(libs.plugins.module.android.library)
    alias(libs.plugins.module.android.presentation)
}

android {
    namespace = "com.gb.opaltest.features.gem.presentation"
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.design)
    implementation(projects.features.gems.domain)
}
