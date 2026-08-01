package ui.content

import androidx.compose.runtime.Immutable
import ui.content.OnboardingData.FindDoctor
import ui.content.OnboardingData.ReminderAndResults
import ui.content.OnboardingData.ScheduleAppointment

@Immutable
sealed class OnboardingData(
    open val title: String,
    open val description: String,
    open val positiveButtonText: String,
    open val negativeButtonText: String,
) {
    data object FindDoctor: OnboardingData(
        title = "Найдите нужного доктора",
        description = "Отсортируйте врачей по специализации, языкам, стоимости и свободному времени.",
        positiveButtonText = "Продолжить",
        negativeButtonText = "Пропустить"
    )

    data object ScheduleAppointment: OnboardingData(
        title = "Запишитесь без звонков",
        description = "Выберите клинику, дату и удобное время прямо в приложении.",
        positiveButtonText = "Продолжить",
        negativeButtonText = "Пропустить"
    )

    data object ReminderAndResults: OnboardingData(
        title = "Напомним о важном",
        description = "Включите уведомления - так мы сможем напомнить вам о предстоящем визите или о готовых анализах. Обещаем не беспокоить по пустякам.",
        positiveButtonText = "Включить уведомления",
        negativeButtonText = "Отказаться"
    )
}

internal fun getOnboardingItemsData() = listOf(
    FindDoctor,
    ScheduleAppointment,
    ReminderAndResults
)