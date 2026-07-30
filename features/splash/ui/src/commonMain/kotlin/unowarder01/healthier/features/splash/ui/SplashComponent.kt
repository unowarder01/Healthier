package unowarder01.healthier.features.splash.ui

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import pro.respawn.flowmvi.compose.dsl.subscribe
import unowarder01.healthier.core.common.AppLanguage
import unowarder01.healthier.core.presentation.component.BaseFeatureComponent
import unowarder01.healthier.features.splash.ui.SplashContract.Action
import unowarder01.healthier.features.splash.ui.SplashContract.Action.NavigateToAuth
import unowarder01.healthier.features.splash.ui.SplashContract.Intent
import unowarder01.healthier.features.splash.ui.SplashContract.Intent.SelectLanguage
import unowarder01.healthier.features.splash.ui.SplashContract.Listener
import unowarder01.healthier.features.splash.ui.SplashContract.State

class SplashComponent(
    context: ComponentContext,
    viewModel: SplashViewModel,
    private val navigator: SplashNavigator
) : Listener, BaseFeatureComponent<State, Intent, Action, SplashViewModel>(
    context = context,
    viewModel = viewModel
) {
    /**
     * STATE
     */
    @Composable
    override fun subscribeState() = subscribe { action ->
        when (action) {
            is NavigateToAuth -> navigator.toAuth()
        }
    }

    /**
     * LISTENER
     */
    override fun onLanguageSelected(language: AppLanguage) {
        intent(SelectLanguage(language))
    }
}
