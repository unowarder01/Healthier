package unowarder01.healthier.core.common

enum class AppLanguage(
    val code: String,
    val englishName: String,
    val nativeName: String
) {
    Georgian("ka", "Georgian", "ქართული"),
    English("en", "English", "English"),
    Russian("ru", "Russian", "Русский");

    companion object {
        fun fromCode(code: String?) = entries.firstOrNull { it.code == code } ?: Georgian
    }
}
