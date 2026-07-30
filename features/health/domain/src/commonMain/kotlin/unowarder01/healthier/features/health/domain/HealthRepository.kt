package unowarder01.healthier.features.health.domain

interface HealthRepository {
    fun doctors(): List<Doctor>
    fun stories(): List<Story>
}
