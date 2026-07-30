plugins { id("healthier.kmp-library") }
kotlin { sourceSets.commonMain.dependencies { implementation(project(":features:home:domain")); implementation(project(":features:home:data")); implementation(project(":features:home:ui")); api(libs.koin.core) } }
