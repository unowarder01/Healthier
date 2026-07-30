package unowarder01.healthier.features.city.domain

import unowarder01.healthier.core.common.AppLanguage

data class City(
    val id: String,
    val names: Map<AppLanguage, String>,
    val aliases: Set<String>,
    val population: Int
) {
    fun name(language: AppLanguage): String =
        names[language] ?: names.getValue(AppLanguage.English)
}
