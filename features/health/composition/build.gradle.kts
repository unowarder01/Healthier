plugins { id("healthier.kmp-library") }

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(project(":features:health:data"))
        implementation(project(":features:health:domain"))
        implementation(project(":features:health:ui"))
        api(libs.koin.core)
    }
}
