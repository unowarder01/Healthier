plugins { id("healthier.kmp-library") }
kotlin { sourceSets.commonMain.dependencies { api(project(":features:auth:domain")); implementation(project(":core:platform")); implementation(project(":core:network")); implementation(libs.koin.core) } }
