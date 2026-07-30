plugins { id("healthier.kmp-library") }
kotlin { sourceSets.commonMain.dependencies { implementation(project(":features:map:domain")); implementation(project(":features:map:data")); implementation(project(":features:map:ui")); api(libs.koin.core) } }
