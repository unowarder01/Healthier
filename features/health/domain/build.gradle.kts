plugins { id("healthier.kmp-library") }
kotlin { sourceSets.commonMain.dependencies { api(project(":core:common")); api(project(":features:city:domain")); implementation(libs.koin.core) } }
