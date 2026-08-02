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
    ":core:mvi",
    ":core:presentation",
)

listOf("splash", "auth", "onboarding").forEach { feature ->
    include(
        ":features:$feature:ui",
        ":features:$feature:composition",
    )
}

include(
    ":features:city:data",
    ":features:city:domain",
    ":features:city:ui",
    ":features:city:composition",
)

listOf("health", "map", "calendar", "profile").forEach { feature ->
    include(
        ":features:$feature:data",
        ":features:$feature:domain",
        ":features:$feature:ui",
        ":features:$feature:composition",
    )
}
