package unowarder01.healthier.features.profile.domain

import kotlinx.coroutines.flow.StateFlow

interface ProfileRepository {
    val profile: StateFlow<Profile>
    suspend fun update(name: String, avatarReference: String?)
}
