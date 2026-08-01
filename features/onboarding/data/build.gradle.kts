plugins { id("healthier.kmp-library") }

kotlin {
    sourceSets.commonMain.dependencies {
        api(project(":features:onboarding:domain"))
        implementation(libs.koin.core)
    }
}
