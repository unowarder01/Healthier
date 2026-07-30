plugins { id("healthier.kmp-library") }
kotlin { sourceSets.commonMain.dependencies { api(project(":core:common")); api(project(":core:preferences")); implementation(libs.koin.core) } }
