plugins { id("healthier.kmp-library") }

kotlin {
    sourceSets.commonMain.dependencies {
        implementation(project(":features:city:data"))
        implementation(project(":features:city:domain"))
        implementation(project(":features:city:ui"))
        api(libs.koin.core)
    }
}
