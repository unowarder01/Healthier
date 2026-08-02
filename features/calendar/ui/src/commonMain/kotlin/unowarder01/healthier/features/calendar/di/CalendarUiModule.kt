package unowarder01.healthier.features.calendar.di

import com.arkivanov.decompose.ComponentContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import unowarder01.healthier.features.calendar.ui.CalendarComponent
import unowarder01.healthier.features.calendar.ui.CalendarNavigator
import unowarder01.healthier.features.calendar.ui.CalendarViewModel

val calendarUiModule = module {
    factoryOf(::CalendarViewModel)
    factory { (context: ComponentContext) ->
        CalendarComponent(
            context = context,
            viewModel = get(),
            navigator = get<CalendarNavigator>(),
        )
    }
}
