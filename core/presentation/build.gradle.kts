plugins {
    id("healthier.kmp-compose")
}

kotlin {
    sourceSets.commonMain.dependencies {
        api(project(":core:common"))
        api(project(":core:mvi"))
        implementation(project(":core:design-system"))
        api(libs.decompose)
        api(libs.decompose.compose)
        api(libs.essenty.lifecycle)
        api(libs.flowmvi.compose)
        implementation(libs.kotlinx.coroutines.core)
    }
}
