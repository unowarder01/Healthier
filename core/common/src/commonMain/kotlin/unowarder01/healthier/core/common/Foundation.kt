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

enum class AppLanguage(val code: String) {
    Georgian("ka"),
    English("en"),
    Russian("ru");

    companion object {
        fun fromCode(code: String?): AppLanguage =
            entries.firstOrNull { it.code == code } ?: English
    }
}

enum class AppTheme {
    System,
    Light,
    Dark;
}

interface DispatcherProvider {
    val default: CoroutineDispatcher
    val io: CoroutineDispatcher
}

object DefaultDispatcherProvider : DispatcherProvider {
    override val default: CoroutineDispatcher = Dispatchers.Default
    override val io: CoroutineDispatcher = Dispatchers.Default
}

fun interface AppLogger {
    fun log(message: String)
}

fun interface Mapper<F, T> {
    fun map(from: F): T
}

fun interface SuspendUseCase<P, R> {
    suspend operator fun invoke(parameters: P): R
}
