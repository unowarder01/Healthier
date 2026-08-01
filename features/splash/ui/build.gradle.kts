plugins {
    id("healthier.kmp-compose")
}

kotlin {
    sourceSets.commonMain.dependencies {
        /**
         * MODULES
         */
        api(project(":features:splash:domain"))
        implementation(project(":core:design-system"))
        implementation(project(":core:presentation"))
        implementation(project(":core:mvi"))
        /**
         * DEPENDENCIES
         */
        implementation(libs.koin.core)
        implementation(libs.compose.constraintLayout)
        implementation(libs.kmpnotifier)
    }
}
