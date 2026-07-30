plugins { id("healthier.kmp-library") }
kotlin { sourceSets.commonMain.dependencies { implementation(project(":features:splash:domain")); implementation(project(":features:splash:data")); implementation(project(":features:splash:ui")); api(libs.koin.core) } }
