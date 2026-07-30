package unowarder01.healthier.features.profile.composition

import org.koin.dsl.module
import unowarder01.healthier.features.profile.data.profileDataModule
import unowarder01.healthier.features.profile.domain.profileDomainModule
import unowarder01.healthier.features.profile.ui.profileUiModule

val profileFeatureModule = module {
    includes(profileDomainModule, profileDataModule, profileUiModule)
}
