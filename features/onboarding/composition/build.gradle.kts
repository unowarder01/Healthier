plugins { id("healthier.kmp-library") }

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(project(":features:onboarding:ui"))
        api(libs.koin.core)
    }
}
