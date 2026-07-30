package unowarder01.healthier.core.common.usecase

interface BaseUseCase<in P, out R> {
    suspend operator fun invoke(params: P): R
}
