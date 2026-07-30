plugins { id("healthier.kmp-compose") }
kotlin { sourceSets.commonMain.dependencies { api(project(":features:home:domain")); implementation(project(":core:design-system")); implementation(project(":core:presentation")); implementation(project(":core:mvi")); implementation(libs.koin.core) } }
