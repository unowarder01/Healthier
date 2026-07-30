plugins { id("healthier.kmp-compose") }
kotlin { sourceSets.commonMain.dependencies { api(project(":features:auth:domain")); implementation(project(":core:design-system")); implementation(project(":core:presentation")); implementation(project(":core:mvi")); implementation(project(":core:platform")); implementation(libs.koin.core) } }
