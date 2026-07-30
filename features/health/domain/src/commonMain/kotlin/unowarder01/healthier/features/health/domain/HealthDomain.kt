package unowarder01.healthier.features.health.domain

import org.koin.dsl.module
import unowarder01.healthier.features.city.domain.Clinic

data class Doctor(
    val id: String,
    val name: String,
    val specialty: String,
)

data class Story(
    val id: String,
    val title: String,
)

interface HealthRepository {
    fun doctors(): List<Doctor>
    fun stories(): List<Story>
}

class GetHealthContentUseCase(
    private val repository: HealthRepository,
) {
    operator fun invoke(clinics: List<Clinic>) = HealthContent(
        clinics = clinics,
        doctors = repository.doctors(),
        stories = repository.stories(),
    )
}

data class HealthContent(
    val clinics: List<Clinic>,
    val doctors: List<Doctor>,
    val stories: List<Story>,
)

val healthDomainModule = module {
    factory { GetHealthContentUseCase(get()) }
}
