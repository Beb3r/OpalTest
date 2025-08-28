plugins {
    alias(libs.plugins.module.android.library)
}

android {
    namespace = "com.gb.opaltest.features.gems.data"
}

dependencies {
    implementation(libs.bundles.datastore)

    implementation(projects.core.common)
    implementation(projects.features.gems.domain)
}
