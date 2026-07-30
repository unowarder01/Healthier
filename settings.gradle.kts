rootProject.name = "Healthier"

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":app:androidApp")
include(":app:shared")
include(":app:webApp")
include(":server")

include(
    ":core:common",
    ":core:design-system",
    ":core:network",
    ":core:database",
    ":core:preferences",
    ":core:mvi",
    ":core:presentation",
    ":core:platform",
)

listOf("splash", "auth", "city", "home", "health", "map", "profile").forEach { feature ->
    include(
        ":features:$feature:domain",
        ":features:$feature:data",
        ":features:$feature:ui",
        ":features:$feature:composition",
    )
}
