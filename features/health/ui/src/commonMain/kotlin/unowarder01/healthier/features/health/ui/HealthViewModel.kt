package unowarder01.healthier.features.health.ui

import kotlinx.coroutines.delay
import pro.respawn.flowmvi.api.PipelineContext
import unowarder01.healthier.core.mvi.currentState
import unowarder01.healthier.core.presentation.viewmodel.BaseViewModel
import unowarder01.healthier.features.city.domain.Clinic
import unowarder01.healthier.features.health.domain.HealthContent
import unowarder01.healthier.features.health.domain.usecase.GetHealthContentUseCase
import unowarder01.healthier.features.health.ui.HealthContract.Action
import unowarder01.healthier.features.health.ui.HealthContract.Intent
import unowarder01.healthier.features.health.ui.HealthContract.Intent.Load
import unowarder01.healthier.features.health.ui.HealthContract.Intent.QueryChanged
import unowarder01.healthier.features.health.ui.HealthContract.State

class HealthViewModel(
    private val content: GetHealthContentUseCase,
    private val clinics: List<Clinic>
) : BaseViewModel<State, Intent, Action>(
    initialState = State(
        content = HealthContent(
            clinics = clinics,
            doctors = emptyList(),
            stories = emptyList()
        )
    ),
    storeKey = "health.overview"
) {
    override suspend fun PipelineContext<State, Intent, Action>.handleIntent(intent: Intent) {
        when (intent) {
            Load -> {
                val value = content(clinics)
                updateState {
                    copy(
                        content = value,
                        filtered = value
                    )
                }
            }
            is QueryChanged -> {
                val value = intent.value
                updateState { copy(query = value) }
                delay(300)
                val term = value.trim()
                val snapshot = currentState()
                val filtered = if (term.isEmpty()) snapshot.content else snapshot.content.copy(
                    clinics = snapshot.content.clinics.filter {
                        it.name.contains(term, true) ||
                            it.specialization.contains(term, true) ||
                            it.address.contains(term, true)
                    },
                    doctors = snapshot.content.doctors.filter {
                        it.name.contains(term, true) || it.specialty.contains(term, true)
                    },
                    stories = snapshot.content.stories.filter { it.title.contains(term, true) }
                )
                updateState { copy(filtered = filtered) }
            }
        }
    }
}
