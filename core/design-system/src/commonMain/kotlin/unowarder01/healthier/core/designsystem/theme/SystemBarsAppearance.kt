package unowarder01.healthier.core.designsystem.theme

import androidx.compose.runtime.Composable

@Composable
internal expect fun SystemBarsAppearance(
    darkTheme: Boolean,
    restoreDarkTheme: Boolean?
)
