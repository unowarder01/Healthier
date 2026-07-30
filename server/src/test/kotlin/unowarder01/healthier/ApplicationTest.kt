package unowarder01.healthier

import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApplicationTest {
    @Test
    fun clinicsEndpointIsVersionedAndCityScoped() = testApplication {
        application { module() }
        val client = createClient {
            install(ContentNegotiation) { json() }
        }

        val response = client.get("/v1/cities/tbilisi/clinics")
        val payload = response.body<ClinicsResponse>()

        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(1, payload.version)
        assertEquals("tbilisi", payload.cityId)
        assertTrue(payload.clinics.all { it.cityId == "tbilisi" })
    }

    @Test
    fun unknownCityReturnsTypedNotFound() = testApplication {
        application { module() }
        val client = createClient {
            install(ContentNegotiation) { json() }
        }

        val response = client.get("/v1/cities/unknown/clinics")
        assertEquals(HttpStatusCode.NotFound, response.status)
        assertEquals("city_not_found", response.body<ErrorResponse>().code)
    }
}
