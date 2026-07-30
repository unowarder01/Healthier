plugins {
    id("healthier.kmp-library")
}

kotlin {
    sourceSets.commonMain.dependencies {
        api(project(":core:common"))
        api(libs.flowmvi.core)
        implementation(libs.kotlinx.coroutines.core)
    }
}
