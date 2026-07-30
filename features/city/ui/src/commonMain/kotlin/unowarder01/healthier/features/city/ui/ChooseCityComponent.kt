package unowarder01.healthier.features.city.ui

import com.arkivanov.decompose.ComponentContext
import unowarder01.healthier.core.presentation.retainedStore

class ChooseCityComponent(
    componentContext: ComponentContext,
    factory: ChooseCityStoreFactory,
    private val navigator: ChooseCityNavigator,
) : ComponentContext by componentContext {
    val store = retainedStore("city.choose-city", factory::create)

    fun handle(action: ChooseCityContract.Action) {
        when (action) {
            is ChooseCityContract.Action.NavigateHome -> navigator.openHome(action.clinics)
        }
    }
}
