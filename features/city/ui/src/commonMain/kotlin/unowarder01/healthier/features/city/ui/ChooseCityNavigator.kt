package unowarder01.healthier.features.city.ui

import unowarder01.healthier.features.city.domain.Clinic

fun interface ChooseCityNavigator {
    fun openHome(clinics: List<Clinic>)
}
