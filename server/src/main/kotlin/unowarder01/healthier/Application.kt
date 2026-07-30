package unowarder01.healthier

import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.Serializable

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) { json() }
    routing {
        get("/") {
            call.respondText("Healthier local demo API")
        }
        get("/v1/cities/{cityId}/clinics") {
            val cityId = call.parameters["cityId"].orEmpty()
            val clinics = demoClinics[cityId]
            if (clinics == null) {
                call.respond(
                    HttpStatusCode.NotFound,
                    ErrorResponse(
                        code = "city_not_found",
                        message = "No demo clinics are configured for this city"
                    )
                )
            } else {
                call.respond(ClinicsResponse(version = 1, cityId = cityId, clinics = clinics))
            }
        }
    }
}

@Serializable
data class ClinicsResponse(
    val version: Int,
    val cityId: String,
    val clinics: List<ClinicResponse>
)

@Serializable
data class ClinicResponse(
    val id: String,
    val cityId: String,
    val name: String,
    val specialization: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String? = null
)

@Serializable
data class ErrorResponse(
    val code: String,
    val message: String
)

private val demoClinics = listOf("tbilisi", "batumi", "kutaisi", "rustavi").associateWith { cityId ->
    listOf(
        ClinicResponse(
            id = "$cityId-central",
            cityId = cityId,
            name = "Healthier Central",
            specialization = "Multidisciplinary clinic",
            address = "Demo address, $cityId",
            latitude = if (cityId == "batumi") 41.6461 else 41.7151,
            longitude = if (cityId == "batumi") 41.6405 else 44.8271
        ),
        ClinicResponse(
            id = "$cityId-family",
            cityId = cityId,
            name = "Family Care",
            specialization = "Family medicine",
            address = "Demo avenue, $cityId",
            latitude = if (cityId == "batumi") 41.6500 else 41.7220,
            longitude = if (cityId == "batumi") 41.6420 else 44.7900
        )
    )
}
