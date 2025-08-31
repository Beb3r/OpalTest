@file:Suppress("UnstableApiUsage")

rootProject.name = "OpalTest"
include(":app")
include(":core:common")
include(":core:design")
include(":core:navigation:api-impl")
include(":core:navigation:api")
include(":core:translations")
include(":features:gems:data")
include(":features:gems:domain")
include(":features:gems:presentation")
include(":features:home:domain")
include(":features:home:navigation")
include(":features:home:presentation")
include(":features:splash-screen:navigation")
include(":features:splash-screen:presentation")
include(":features:rewards:data")
include(":features:rewards:domain")
include(":features:referral:data")
include(":features:referral:domain")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven("https://jitpack.io")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        maven("https://jitpack.io")
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
