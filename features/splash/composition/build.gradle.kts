plugins { id("healthier.kmp-library") }

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(project(":features:splash:ui"))
        api(libs.koin.core)
    }
}
