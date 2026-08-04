package unowarder01.healthier.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val HealthierLightColorScheme = lightColorScheme(
    primary = Color(0xFF0F5AC7),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFEAF2FF),
    onPrimaryContainer = Color(0xFF0B3A78),

    secondary = Color(0xFF667085),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFF2F4F7),
    onSecondaryContainer = Color(0xFF171A1F),

    tertiary = Color(0xFF168A68),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFDDF5EC),
    onTertiaryContainer = Color(0xFF074E3B),

    error = Color(0xFFD92D20),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFEE4E2),
    onErrorContainer = Color(0xFF7A271A),

    background = Color(0xFFFCFCFD),
    onBackground = Color(0xFF171A1F),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF171A1F),

    surfaceVariant = Color(0xFFF2F4F7),
    onSurfaceVariant = Color(0xFF667085),

    surfaceBright = Color(0xFFFFFFFF),
    surfaceDim = Color(0xFFF2F4F7),

    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFFCFCFD),
    surfaceContainer = Color(0xFFF6F7F9),
    surfaceContainerHigh = Color(0xFFF2F4F7),
    surfaceContainerHighest = Color(0xFFE7E9ED),

    outline = Color(0xFFD9DDE3),
    outlineVariant = Color(0xFFE7E9ED),

    inverseSurface = Color(0xFF1E2B3F),
    inverseOnSurface = Color(0xFFF7F9FC),
    inversePrimary = Color(0xFF58A6FF),

    scrim = Color(0xFF0F1724),
    surfaceTint = Color(0xFF0F5AC7)
)

private val HealthierDarkColorScheme = darkColorScheme(
    primary = Color(0xFF58A6FF),
    onPrimary = Color(0xFF071A2E),
    primaryContainer = Color(0xFF203F67),
    onPrimaryContainer = Color(0xFFD9EAFF),

    secondary = Color(0xFFAAB6C7),
    onSecondary = Color(0xFF151E2D),
    secondaryContainer = Color(0xFF25364D),
    onSecondaryContainer = Color(0xFFF7F9FC),

    tertiary = Color(0xFF5EC49B),
    onTertiary = Color(0xFF062F25),
    tertiaryContainer = Color(0xFF164F40),
    onTertiaryContainer = Color(0xFFDDF5EC),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    background = Color(0xFF151E2D),
    onBackground = Color(0xFFF7F9FC),

    surface = Color(0xFF1E2B3F),
    onSurface = Color(0xFFF7F9FC),

    surfaceVariant = Color(0xFF25364D),
    onSurfaceVariant = Color(0xFFAAB6C7),

    surfaceBright = Color(0xFF25364D),
    surfaceDim = Color(0xFF151E2D),

    surfaceContainerLowest = Color(0xFF151E2D),
    surfaceContainerLow = Color(0xFF1E2B3F),
    surfaceContainer = Color(0xFF242F3F),
    surfaceContainerHigh = Color(0xFF25364D),
    surfaceContainerHighest = Color(0xFF34465A),

    outline = Color(0xFF34465A),
    outlineVariant = Color(0xFF29394B),

    inverseSurface = Color(0xFFF7F9FC),
    inverseOnSurface = Color(0xFF151E2D),
    inversePrimary = Color(0xFF0F5AC7),

    scrim = Color(0xFF000000),
    surfaceTint = Color(0xFF58A6FF)
)

private val HealthierTypography = Typography(
    displayLarge = healthierTextStyle(
        weight = FontWeight.Bold,
        fontSize = 34,
        lineHeight = 41
    ),
    displayMedium = healthierTextStyle(
        weight = FontWeight.Bold,
        fontSize = 28,
        lineHeight = 34
    ),
    displaySmall = healthierTextStyle(
        weight = FontWeight.SemiBold,
        fontSize = 22,
        lineHeight = 28
    ),
    headlineLarge = healthierTextStyle(
        weight = FontWeight.Bold,
        fontSize = 28,
        lineHeight = 34
    ),
    headlineMedium = healthierTextStyle(
        weight = FontWeight.SemiBold,
        fontSize = 22,
        lineHeight = 28
    ),
    headlineSmall = healthierTextStyle(
        weight = FontWeight.SemiBold,
        fontSize = 20,
        lineHeight = 25
    ),
    titleLarge = healthierTextStyle(
        weight = FontWeight.SemiBold,
        fontSize = 20,
        lineHeight = 25
    ),
    titleMedium = healthierTextStyle(
        weight = FontWeight.SemiBold,
        fontSize = 17,
        lineHeight = 22
    ),
    titleSmall = healthierTextStyle(
        weight = FontWeight.Medium,
        fontSize = 15,
        lineHeight = 20
    ),
    bodyLarge = healthierTextStyle(
        weight = FontWeight.Normal,
        fontSize = 17,
        lineHeight = 23
    ),
    bodyMedium = healthierTextStyle(
        weight = FontWeight.Normal,
        fontSize = 15,
        lineHeight = 20
    ),
    bodySmall = healthierTextStyle(
        weight = FontWeight.Normal,
        fontSize = 13,
        lineHeight = 18
    ),
    labelLarge = healthierTextStyle(
        weight = FontWeight.Medium,
        fontSize = 16,
        lineHeight = 21
    ),
    labelMedium = healthierTextStyle(
        weight = FontWeight.Medium,
        fontSize = 13,
        lineHeight = 18
    ),
    labelSmall = healthierTextStyle(
        weight = FontWeight.Normal,
        fontSize = 12,
        lineHeight = 16
    )
)

private fun healthierTextStyle(
    weight: FontWeight,
    fontSize: Int,
    lineHeight: Int
) = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = weight,
    fontSize = fontSize.sp,
    lineHeight = lineHeight.sp
)

private val HealthierShapes = Shapes(
    extraSmall = RoundedCornerShape(12.dp),
    small = RoundedCornerShape(16.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

private val LocalHealthierDarkTheme = staticCompositionLocalOf<Boolean?> { null }

@Composable
fun HealthierTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val parentDarkTheme = LocalHealthierDarkTheme.current
    SystemBarsAppearance(
        darkTheme = darkTheme,
        restoreDarkTheme = parentDarkTheme
    )
    CompositionLocalProvider(LocalHealthierDarkTheme provides darkTheme) {
        MaterialTheme(
            colorScheme = if (darkTheme) HealthierDarkColorScheme else HealthierLightColorScheme,
            typography = HealthierTypography,
            shapes = HealthierShapes,
            content = content
        )
    }
}
