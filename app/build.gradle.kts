import com.gb.opaltest.build_logic.implementation
import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.module.android.application)
    alias(libs.plugins.module.android.presentation)
}

android {
    namespace = "com.gb.opaltest.app"

    defaultConfig {
        applicationId = "com.gb.opaltest"
    }

    androidResources {
        @Suppress("UnstableApiUsage")
        localeFilters += listOf("en", "fr")
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    buildTypes {
        maybeCreate("debug")
        maybeCreate("release")
        named("release") {
            signingConfig = signingConfigs.getByName("debug")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.navigation)
    implementation(libs.bundles.coil)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(projects.core.common)
    implementation(projects.core.design)
    implementation(projects.core.navigation.api)
    implementation(projects.core.navigation.apiImpl)
    implementation(projects.core.translations)
    implementation(projects.features.gems.data)
    implementation(projects.features.gems.domain)
    implementation(projects.features.gems.presentation)
    implementation(projects.features.home.domain)
    implementation(projects.features.home.navigation)
    implementation(projects.features.home.presentation)
    implementation(projects.features.referral.data)
    implementation(projects.features.referral.domain)
    implementation(projects.features.rewards.data)
    implementation(projects.features.rewards.domain)
    implementation(projects.features.splashScreen.navigation)
    implementation(projects.features.splashScreen.presentation)
}
