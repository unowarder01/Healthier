plugins {
    id("healthier.kmp-library")
}

kotlin {
    sourceSets.commonMain.dependencies {
        api(project(":core:common"))
        api(libs.multiplatform.settings)
        api(libs.multiplatform.settings.noarg)
        implementation(libs.kotlinx.coroutines.core)
    }
}
