import org.gradle.initialization.DependenciesAccessors
import org.gradle.kotlin.dsl.support.serviceOf

plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradle.plugin)

    gradle.serviceOf<DependenciesAccessors>().classes.asFiles.forEach {
        compileOnly(files(it.absolutePath))
    }
}

group = "com.gb.opaltest.build-logic"

gradlePlugin {
    plugins {
        register("moduleAndroidApplication") {
            id = "com.gb.opaltest.module.android.application"
            implementationClass = "com.gb.opaltest.build_logic.AndroidApplicationModulePlugin"
        }
        register("moduleAndroidLibrary") {
            id = "com.gb.opaltest.module.android.library"
            implementationClass = "com.gb.opaltest.build_logic.AndroidLibraryModulePlugin"
        }
        register("moduleAndroidPresentation") {
            id = "com.gb.opaltest.module.android.presentation"
            implementationClass = "com.gb.opaltest.build_logic.AndroidPresentationModulePlugin"
        }
    }
}
