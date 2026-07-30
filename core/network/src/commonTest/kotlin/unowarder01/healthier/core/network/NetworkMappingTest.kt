package unowarder01.healthier.core.network

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertIs
import unowarder01.healthier.core.common.AppError
import unowarder01.healthier.core.common.AppResult

class NetworkMappingTest {
    @Test
    fun mapsSuccessAndConnectionFailure() = runTest {
        assertEquals(AppResult.Success(42), mapNetworkCall { 42 })

        val failure = mapNetworkCall<Int> { error("connection refused") }
        assertEquals(AppError.Offline, assertIs<AppResult.Failure>(failure).error)
    }

    @Test
    fun propagatesCancellation() = runTest {
        assertFailsWith<CancellationException> {
            mapNetworkCall<Int> { throw CancellationException("cancelled") }
        }
    }
}
