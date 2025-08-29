import com.gb.opaltest.build_logic.implementation
import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.module.android.library)
}

android {
    namespace = "com.gb.opaltest.libraries.navigation.api_impl"
}

dependencies{
    implementation(libs.androidx.navigation)

    implementation(projects.core.common)
    implementation(projects.core.navigation.api)
    implementation(projects.features.home.navigation)
    implementation(projects.features.splashScreen.navigation)
}
