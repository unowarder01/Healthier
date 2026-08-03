package unowarder01.healthier.core.common.usecase

interface UseCaseNoArg {
    suspend operator fun invoke()
}

interface UseCaseParam<T> {
    suspend operator fun invoke(input: T)
}

interface UseCaseResult<R> {
    suspend operator fun invoke(): R
}

interface UseCase<T, R> {
    suspend operator fun invoke(input: T): R
}
