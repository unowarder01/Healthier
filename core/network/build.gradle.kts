plugins {
    id("healthier.kmp-library")
}

kotlin {
    sourceSets.commonMain.dependencies {
        /**
         * MODULES
         */
        implementation(project(":core:common"))
        /**
         * DEPENDENCIES
         */
        implementation(libs.koin.core)
        implementation(libs.kotlinx.coroutines.core)
    }
}