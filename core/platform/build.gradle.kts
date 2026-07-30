plugins {
    id("healthier.kmp-library")
}

kotlin {
    sourceSets.commonMain.dependencies {
        api(project(":core:common"))
        implementation(libs.kotlinx.coroutines.core)
    }
}
