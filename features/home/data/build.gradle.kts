plugins { id("healthier.kmp-library") }
kotlin { sourceSets.commonMain.dependencies { api(project(":features:home:domain")); implementation(libs.koin.core) } }
