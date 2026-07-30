package unowarder01.healthier.features.auth.ui

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import unowarder01.healthier.core.common.AppError
import unowarder01.healthier.core.common.AppResult
import unowarder01.healthier.core.platform.AuthToken
import unowarder01.healthier.core.platform.SocialAuthProvider
import unowarder01.healthier.core.platform.SocialProvider
import unowarder01.healthier.features.auth.domain.usecase.AuthenticateUseCase

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class AuthStoreTest {
    @Test
    fun successClearsLoadingAndNavigates() = runTest {
        val repository = FakeAuthRepository(AppResult.Success(Unit))
        val store = AuthViewModel(
            authenticate = repository,
            provider = FakeProvider
        ).store
        var latest = AuthContract.State()
        val actions = mutableListOf<AuthContract.Action>()
        store.start(backgroundScope)
        with(store) {
            backgroundScope.subscribe { states.collect { latest = it } }
            backgroundScope.subscribe { this.actions.collect { actions += it } }
        }
        runCurrent()

        store.intent(AuthContract.Intent.Reveal)
        runCurrent()
        store.intent(AuthContract.Intent.Authenticate(SocialProvider.Google))
        runCurrent()

        assertTrue(latest.visible)
        assertNull(latest.loadingProvider)
        assertNull(latest.error)
        assertEquals(SocialProvider.Google, repository.requested)
        assertTrue(AuthContract.Action.NavigateToCity in actions)
    }

    @Test
    fun failureBecomesVisibleError() = runTest {
        val repository = FakeAuthRepository(AppResult.Failure(AppError.NotConfigured))
        val store = AuthViewModel(
            authenticate = repository,
            provider = FakeProvider
        ).store
        var latest = AuthContract.State()
        store.start(backgroundScope)
        with(store) { backgroundScope.subscribe { states.collect { latest = it } } }
        runCurrent()

        store.intent(AuthContract.Intent.Authenticate(SocialProvider.Google))
        runCurrent()

        assertNull(latest.loadingProvider)
        assertEquals("auth_failed", latest.error)
    }
}

private class FakeAuthRepository(
    private val result: AppResult<Unit>
) : AuthenticateUseCase {
    var requested: SocialProvider? = null
    override suspend fun invoke(params: SocialProvider): AppResult<Unit> {
        requested = params
        return result
    }
}

private object FakeProvider : SocialAuthProvider {
    override val availableProviders = setOf(SocialProvider.Google)
    override suspend fun authenticate(provider: SocialProvider): AppResult<AuthToken> =
        AppResult.Failure(AppError.NotConfigured)
}
