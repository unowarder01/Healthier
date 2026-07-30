package unowarder01.healthier.features.auth.composition

import org.koin.dsl.module
import unowarder01.healthier.features.auth.data.authDataModule
import unowarder01.healthier.features.auth.domain.authDomainModule
import unowarder01.healthier.features.auth.ui.authUiModule

val authFeatureModule = module {
    includes(authDomainModule, authDataModule, authUiModule)
}
