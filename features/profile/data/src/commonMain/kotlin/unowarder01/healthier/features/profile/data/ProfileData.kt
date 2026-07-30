package unowarder01.healthier.features.profile.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.dsl.module
import unowarder01.healthier.features.profile.domain.Profile
import unowarder01.healthier.features.profile.domain.ProfileRepository

class DemoProfileRepository : ProfileRepository {
    private val state = MutableStateFlow(Profile(name = "Healthier Demo", avatarReference = null))
    override val profile: StateFlow<Profile> = state.asStateFlow()

    override suspend fun update(name: String, avatarReference: String?) {
        state.value = Profile(
            name = name.ifBlank { state.value.name },
            avatarReference = avatarReference ?: state.value.avatarReference,
        )
    }
}

val profileDataModule = module {
    single<ProfileRepository> { DemoProfileRepository() }
}
