package unowarder01.healthier.features.health.data

import org.koin.dsl.module
import unowarder01.healthier.features.health.domain.Doctor
import unowarder01.healthier.features.health.domain.HealthRepository
import unowarder01.healthier.features.health.domain.Story

class DemoHealthRepository : HealthRepository {
    override fun doctors() = listOf(
        Doctor("doctor-1", "Nino K.", "Family medicine"),
        Doctor("doctor-2", "Giorgi M.", "Cardiology"),
        Doctor("doctor-3", "Ana D.", "Diagnostics"),
    )

    override fun stories() = listOf(
        Story("story-1", "Check-up"),
        Story("story-2", "Prevention"),
        Story("story-3", "Family care"),
        Story("story-4", "Diagnostics"),
    )
}

val healthDataModule = module {
    single<HealthRepository> { DemoHealthRepository() }
}
