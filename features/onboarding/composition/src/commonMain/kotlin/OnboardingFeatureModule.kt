import di.onboardingUiModule
import org.koin.dsl.module

val onboardingFeatureModule = module {
    includes(onboardingUiModule)
}