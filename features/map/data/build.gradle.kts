plugins { id("healthier.kmp-library") }
kotlin { sourceSets.commonMain.dependencies { api(project(":features:map:domain")); implementation(project(":features:city:domain")); implementation(libs.koin.core) } }
