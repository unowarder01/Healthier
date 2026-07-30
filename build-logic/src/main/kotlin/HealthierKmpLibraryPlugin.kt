import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class HealthierKmpLibraryPlugin : Plugin<Project> {
    @OptIn(ExperimentalWasmDsl::class)
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.multiplatform")
        pluginManager.apply("com.android.kotlin.multiplatform.library")

        val libs = extensions.getByType(VersionCatalogsExtension::class.java).named("libs")
        extensions.configure<KotlinMultiplatformExtension> {
            iosArm64()
            iosSimulatorArm64()
            js { browser() }
            wasmJs { browser() }

            targets.withType(KotlinMultiplatformAndroidLibraryTarget::class.java).configureEach {
                namespace = "unowarder01.healthier" + project.path
                    .replace(':', '.')
                    .replace('-', '_')
                compileSdk = libs.findVersion("android-compileSdk").get().requiredVersion.toInt()
                minSdk = libs.findVersion("android-minSdk").get().requiredVersion.toInt()
                // Compose Multiplatform stores its generated .cvr files in Android assets.
                // The Android-KMP library plugin disables resource processing by default.
                androidResources {
                    enable = true
                }
                withHostTest {}
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_11)
                }
            }

            applyDefaultHierarchyTemplate()
            sourceSets.apply {
                val commonMain = getByName("commonMain")
                val nonWebMain = maybeCreate("nonWebMain").apply { dependsOn(commonMain) }
                getByName("androidMain").dependsOn(nonWebMain)
                getByName("iosMain").dependsOn(nonWebMain)

                getByName("commonTest").dependencies {
                    implementation(libs.findLibrary("kotlin-test").get())
                    implementation(libs.findLibrary("kotlinx-coroutines-test").get())
                }
            }
        }
    }
}
