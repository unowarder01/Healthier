package unowarder01.healthier.features.auth.ui.content

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import unowarder01.healthier.designsystem.generated.resources.Res
import unowarder01.healthier.designsystem.generated.resources.continue_with_apple
import unowarder01.healthier.designsystem.generated.resources.continue_with_google
import unowarder01.healthier.designsystem.generated.resources.continue_with_meta
import unowarder01.healthier.designsystem.generated.resources.continue_with_telegram
import unowarder01.healthier.designsystem.generated.resources.ic_apple
import unowarder01.healthier.designsystem.generated.resources.ic_google
import unowarder01.healthier.designsystem.generated.resources.ic_meta
import unowarder01.healthier.designsystem.generated.resources.ic_telegram
import unowarder01.healthier.features.auth.ui.content.SocialProviderUi.Apple
import unowarder01.healthier.features.auth.ui.content.SocialProviderUi.Google
import unowarder01.healthier.features.auth.ui.content.SocialProviderUi.Meta
import unowarder01.healthier.features.auth.ui.content.SocialProviderUi.Telegram

@Immutable
sealed class SocialProviderUi(
    val text: StringResource,
    val icon: DrawableResource,
) {
    data object Apple : SocialProviderUi(
        text = Res.string.continue_with_apple,
        icon = Res.drawable.ic_apple,
    )

    data object Google : SocialProviderUi(
        text = Res.string.continue_with_google,
        icon = Res.drawable.ic_google,
    )

    data object Meta : SocialProviderUi(
        text = Res.string.continue_with_meta,
        icon = Res.drawable.ic_meta,
    )

    data object Telegram : SocialProviderUi(
        text = Res.string.continue_with_telegram,
        icon = Res.drawable.ic_telegram,
    )
}

internal fun getSocialProvidersUi() = listOf(
    Apple,
    Google,
    Meta,
    Telegram,
)
