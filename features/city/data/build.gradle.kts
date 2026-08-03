plugins { id("healthier.kmp-library") }

kotlin {
    sourceSets.commonMain.dependencies {
        /**
         * MODULES
         */
        implementation(project(":core:common"))
        implementation(project(":core:network"))
        implementation(project(":features:city:domain"))
        /**
         * DEPENDENCIES
         */
        implementation(libs.koin.core)
        implementation(libs.kotlinx.coroutines.core)
    }
}
