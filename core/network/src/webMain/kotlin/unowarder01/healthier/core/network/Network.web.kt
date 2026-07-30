package unowarder01.healthier.core.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.js.Js

actual fun platformHttpClientEngine(): HttpClientEngine = Js.create()
