plugins { id("healthier.kmp-library") }
kotlin { sourceSets.commonMain.dependencies { implementation(project(":features:profile:domain")); implementation(project(":features:profile:data")); implementation(project(":features:profile:ui")); api(libs.koin.core) } }
