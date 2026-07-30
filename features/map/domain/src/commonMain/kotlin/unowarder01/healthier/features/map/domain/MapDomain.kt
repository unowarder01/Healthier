package unowarder01.healthier.features.map.domain

import org.koin.dsl.module
import unowarder01.healthier.features.city.domain.Clinic

data class ClinicMarker(
    val clinic: Clinic,
)

val mapDomainModule = module { }
