plugins { id("healthier.kmp-compose") }
kotlin { sourceSets.commonMain.dependencies { api(project(":features:profile:domain")); implementation(project(":core:design-system")); implementation(project(":core:presentation")); implementation(project(":core:mvi")); implementation(project(":core:platform")); implementation(libs.coil.compose); implementation(libs.koin.core) } }
