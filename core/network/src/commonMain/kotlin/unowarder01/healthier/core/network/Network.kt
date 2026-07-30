package unowarder01.healthier.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.json.Json
import unowarder01.healthier.core.common.AppError
import unowarder01.healthier.core.common.AppResult

data class NetworkEnvironment(
    val baseUrl: String,
    val isDebug: Boolean
)

fun createHttpClient(
    engine: HttpClientEngine,
    environment: NetworkEnvironment
): HttpClient = HttpClient(engine) {
    expectSuccess = false
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            }
        )
    }
    install(HttpTimeout) {
        connectTimeoutMillis = 10_000
        requestTimeoutMillis = 15_000
        socketTimeoutMillis = 15_000
    }
    install(Logging) {
        level = if (environment.isDebug) LogLevel.INFO else LogLevel.NONE
        sanitizeHeader { header -> header.equals("Authorization", ignoreCase = true) }
        logger = object : Logger {
            override fun log(message: String) {
                // Intentionally no-op: network/auth/profile payloads are not logged.
            }
        }
    }
}

expect fun platformHttpClientEngine(): HttpClientEngine

suspend inline fun <T> mapNetworkCall(block: () -> T): AppResult<T> = try {
    AppResult.Success(block())
} catch (cancelled: CancellationException) {
    throw cancelled
} catch (_: HttpRequestTimeoutException) {
    AppResult.Failure(AppError.Timeout)
} catch (error: Throwable) {
    val message = error.message.orEmpty().lowercase()
    val mapped = if ("connect" in message || "network" in message || "offline" in message) {
        AppError.Offline
    } else {
        AppError.Unknown("network_error")
    }
    AppResult.Failure(mapped)
}
