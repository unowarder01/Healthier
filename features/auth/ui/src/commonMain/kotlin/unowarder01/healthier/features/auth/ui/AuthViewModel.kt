package unowarder01.healthier.features.auth.ui

import org.koin.dsl.module
import unowarder01.healthier.core.common.AppResult
import unowarder01.healthier.core.mvi.healthierStore
import unowarder01.healthier.core.mvi.currentState
import unowarder01.healthier.core.platform.SocialAuthProvider
import unowarder01.healthier.core.presentation.StoreViewModel
import unowarder01.healthier.features.auth.domain.AuthenticateUseCase

class AuthStoreFactory(
    private val authenticate: AuthenticateUseCase,
    private val provider: SocialAuthProvider,
) {
    val availableProviders get() = provider.availableProviders

    fun create() = healthierStore<AuthContract.State, AuthContract.Intent, AuthContract.Action>(
        name = "auth.social",
        initial = AuthContract.State(),
    ) { intent ->
        when (intent) {
            AuthContract.Intent.Reveal -> updateState { copy(visible = true) }
            is AuthContract.Intent.Authenticate -> {
                if (currentState().loadingProvider != null) return@healthierStore
                updateState { copy(loadingProvider = intent.provider, error = null) }
                when (authenticate(intent.provider)) {
                    is AppResult.Success -> {
                        updateState { copy(loadingProvider = null) }
                        action(AuthContract.Action.NavigateToCity)
                    }
                    is AppResult.Failure ->
                        updateState { copy(loadingProvider = null, error = "auth_failed") }
                }
            }
        }
    }
}

class AuthViewModel(factory: AuthStoreFactory) :
    StoreViewModel<AuthContract.State, AuthContract.Intent, AuthContract.Action>(factory.create())

val authUiModule = module {
    factory { AuthStoreFactory(get(), get()) }
}
