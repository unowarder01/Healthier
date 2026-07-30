plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.android.gradlePlugin)
    implementation(libs.compose.gradlePlugin)
    implementation(libs.compose.compilerGradlePlugin)
}

gradlePlugin {
    plugins {
        register("healthierKmpLibrary") {
            id = "healthier.kmp-library"
            implementationClass = "HealthierKmpLibraryPlugin"
        }
        register("healthierKmpCompose") {
            id = "healthier.kmp-compose"
            implementationClass = "HealthierKmpComposePlugin"
        }
    }
}
