plugins { id("healthier.kmp-compose") }

kotlin {
    sourceSets.androidMain.dependencies {
        implementation(libs.google.maps.compose)
    }

    sourceSets.commonMain.dependencies {
        implementation(project(":core:design-system"))
        implementation(project(":core:presentation"))
        implementation(project(":core:mvi"))
        implementation(libs.koin.core)
    }
}
