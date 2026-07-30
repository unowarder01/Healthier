plugins {
    id("healthier.kmp-library")
    alias(libs.plugins.kotlinSerialization)
}
kotlin { sourceSets.commonMain.dependencies { api(project(":features:city:domain")); implementation(project(":core:network")); implementation(project(":core:database")); implementation(project(":core:preferences")); implementation(libs.kotlinx.serialization.json); implementation(libs.koin.core) } }
