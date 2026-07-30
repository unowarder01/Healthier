package unowarder01.healthier.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

sealed interface AppResult<out T> {
    data class Success<T>(val value: T) : AppResult<T>
    data class Failure(val error: AppError) : AppResult<Nothing>
}

sealed interface AppError {
    data object Cancelled : AppError
    data object Offline : AppError
    data object Timeout : AppError
    data object NotConfigured : AppError
    data object NotFound : AppError
    data class Http(val status: Int) : AppError
    data class InvalidData(val reason: String) : AppError
    data class Unknown(val safeMessage: String) : AppError
}

enum class AppLanguage(
    val code: String,
    val englishName: String,
    val nativeName: String,
    val flag: String
) {
    Georgian(
        code = "ka",
        englishName = "Georgian",
        nativeName = "ქართული",
        flag = "🇬🇪"
    ),
    English(
        code = "en",
        englishName = "English",
        nativeName = "English",
        flag = "🇬🇧"
    ),
    Russian(
        code = "ru",
        englishName = "Russian",
        nativeName = "Русский",
        flag = "🇷🇺"
    );

    companion object {
        fun fromCode(code: String?) = entries
            .firstOrNull { it.code == code }
            ?: Georgian
    }
}

enum class AppTheme {
    System,
    Light,
    Dark;
}
