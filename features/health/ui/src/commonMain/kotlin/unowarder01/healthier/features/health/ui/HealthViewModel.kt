package unowarder01.healthier.features.health.ui

import kotlinx.coroutines.delay
import org.koin.dsl.module
import unowarder01.healthier.core.mvi.healthierStore
import unowarder01.healthier.core.mvi.currentState
import unowarder01.healthier.core.presentation.StoreViewModel
import unowarder01.healthier.features.city.domain.Clinic
import unowarder01.healthier.features.health.domain.GetHealthContentUseCase

class HealthStoreFactory(
    private val content: GetHealthContentUseCase,
) {
    fun create(clinics: List<Clinic>) =
        healthierStore<HealthContract.State, HealthContract.Intent, HealthContract.Action>(
            name = "health.overview",
            initial = HealthContract.State(content = content(clinics)),
        ) { intent ->
            when (intent) {
                is HealthContract.Intent.QueryChanged -> {
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
                        stories = snapshot.content.stories.filter { it.title.contains(term, true) },
                    )
                    updateState { copy(filtered = filtered) }
                }
            }
        }
}

class HealthViewModel(factory: HealthStoreFactory, clinics: List<Clinic>) :
    StoreViewModel<HealthContract.State, HealthContract.Intent, HealthContract.Action>(
        factory.create(clinics)
    )

val healthUiModule = module {
    factory { HealthStoreFactory(get()) }
}
