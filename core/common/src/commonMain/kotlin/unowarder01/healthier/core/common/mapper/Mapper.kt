package unowarder01.healthier.core.common.mapper

interface Mapper<From, To> {
    fun map(from: From): To
}
