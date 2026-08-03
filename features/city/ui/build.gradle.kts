plugins { id("healthier.kmp-compose") }

kotlin {
    sourceSets.commonMain.dependencies {
        /**
         * MODULES
         */
        implementation(project(":core:design-system"))
        implementation(project(":core:presentation"))
        implementation(project(":core:mvi"))
        implementation(project(":features:city:domain"))
        /**
         * DEPENDENCIES
         */
        implementation(libs.koin.core)
    }
}
