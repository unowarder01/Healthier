plugins { id("healthier.kmp-library") }
kotlin { sourceSets.commonMain.dependencies { implementation(project(":features:auth:domain")); implementation(project(":features:auth:data")); implementation(project(":features:auth:ui")); api(libs.koin.core) } }
