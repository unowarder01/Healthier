package unowarder01.healthier.core.common.mapper

interface SuspendMapper<From, To> {
    suspend fun map(from: From): To
}