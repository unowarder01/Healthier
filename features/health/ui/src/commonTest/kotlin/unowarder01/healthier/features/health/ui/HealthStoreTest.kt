package unowarder01.healthier.features.health.ui

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import unowarder01.healthier.features.city.domain.Clinic
import unowarder01.healthier.features.health.domain.Doctor
import unowarder01.healthier.features.health.domain.GetHealthContentUseCase
import unowarder01.healthier.features.health.domain.HealthRepository
import unowarder01.healthier.features.health.domain.Story

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class HealthStoreTest {
    @Test
    fun searchFiltersClinicsDoctorsAndStories() = runTest {
        val repository = object : HealthRepository {
            override fun doctors() = listOf(Doctor("d1", "Nino Heart", "Cardiology"))
            override fun stories() = listOf(Story("s1", "Healthy heart"))
        }
        val clinics = listOf(
            Clinic("c1", "tbilisi", "Heart Center", "Cardiology", "One", 1.0, 2.0, null),
            Clinic("c2", "tbilisi", "Dental Center", "Dentistry", "Two", 1.0, 2.0, null),
        )
        val store = HealthStoreFactory(GetHealthContentUseCase(repository)).create(clinics)
        var latest = HealthContract.State(content = GetHealthContentUseCase(repository)(clinics))
        store.start(backgroundScope)
        with(store) { backgroundScope.subscribe { states.collect { latest = it } } }
        runCurrent()

        store.intent(HealthContract.Intent.QueryChanged(" heart "))
        runCurrent()
        advanceTimeBy(301)
        runCurrent()

        assertEquals(" heart ", latest.query)
        assertEquals(listOf("c1"), latest.filtered.clinics.map(Clinic::id))
        assertEquals(listOf("d1"), latest.filtered.doctors.map(Doctor::id))
        assertEquals(listOf("s1"), latest.filtered.stories.map(Story::id))
    }
}
