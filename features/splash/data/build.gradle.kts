plugins { id("healthier.kmp-library") }
kotlin { sourceSets.commonMain.dependencies { api(project(":features:splash:domain")); implementation(project(":core:preferences")); implementation(libs.koin.core) } }
