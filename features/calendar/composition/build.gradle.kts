plugins { id("healthier.kmp-library") }

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(project(":features:calendar:data"))
        implementation(project(":features:calendar:domain"))
        implementation(project(":features:calendar:ui"))
        api(libs.koin.core)
    }
}
