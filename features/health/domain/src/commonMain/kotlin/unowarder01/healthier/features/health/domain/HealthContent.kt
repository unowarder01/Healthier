package unowarder01.healthier.features.health.domain

import unowarder01.healthier.features.city.domain.Clinic

data class HealthContent(
    val clinics: List<Clinic>,
    val doctors: List<Doctor>,
    val stories: List<Story>
)
