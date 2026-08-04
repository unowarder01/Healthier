plugins {
    id("healthier.kmp-compose")
}

kotlin {
    sourceSets.androidMain.dependencies {
        implementation(libs.androidx.core.ktx)
    }
    sourceSets.commonMain.dependencies {
        api(project(":core:common"))
        api(libs.compose.runtime)
        api(libs.compose.foundation)
        api(libs.compose.material3)
        api("org.jetbrains.compose.material:material-icons-extended:1.7.3")
        api(libs.compose.ui)
        api(libs.compose.components.resources)
        implementation(libs.coil.compose)
        implementation(libs.coil.network.ktor)
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "unowarder01.healthier.designsystem.generated.resources"
}
