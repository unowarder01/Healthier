package ui.content

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.StringResource
import ui.content.OnboardingData.FindDoctor
import ui.content.OnboardingData.ReminderAndResults
import ui.content.OnboardingData.ScheduleAppointment
import unowarder01.healthier.designsystem.generated.resources.Res
import unowarder01.healthier.designsystem.generated.resources.continue_action
import unowarder01.healthier.designsystem.generated.resources.decline
import unowarder01.healthier.designsystem.generated.resources.enable_notifications
import unowarder01.healthier.designsystem.generated.resources.onboarding_find_doctor_description
import unowarder01.healthier.designsystem.generated.resources.onboarding_find_doctor_title
import unowarder01.healthier.designsystem.generated.resources.onboarding_reminder_description
import unowarder01.healthier.designsystem.generated.resources.onboarding_reminder_title
import unowarder01.healthier.designsystem.generated.resources.onboarding_schedule_appointment_description
import unowarder01.healthier.designsystem.generated.resources.onboarding_schedule_appointment_title
import unowarder01.healthier.designsystem.generated.resources.skip

@Immutable
sealed class OnboardingData(
    open val title: StringResource,
    open val description: StringResource,
    open val positiveButtonText: StringResource,
    open val negativeButtonText: StringResource,
) {
    data object FindDoctor: OnboardingData(
        title = Res.string.onboarding_find_doctor_title,
        description = Res.string.onboarding_find_doctor_description,
        positiveButtonText = Res.string.continue_action,
        negativeButtonText = Res.string.skip
    )

    data object ScheduleAppointment: OnboardingData(
        title = Res.string.onboarding_schedule_appointment_title,
        description = Res.string.onboarding_schedule_appointment_description,
        positiveButtonText = Res.string.continue_action,
        negativeButtonText = Res.string.skip
    )

    data object ReminderAndResults: OnboardingData(
        title = Res.string.onboarding_reminder_title,
        description = Res.string.onboarding_reminder_description,
        positiveButtonText = Res.string.enable_notifications,
        negativeButtonText = Res.string.decline
    )
}

internal fun getOnboardingItemsData() = listOf(
    FindDoctor,
    ScheduleAppointment,
    ReminderAndResults
)
