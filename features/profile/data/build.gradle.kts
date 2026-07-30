plugins { id("healthier.kmp-library") }
kotlin { sourceSets.commonMain.dependencies { api(project(":features:profile:domain")); implementation(project(":core:preferences")); implementation(project(":core:platform")); implementation(libs.koin.core) } }
