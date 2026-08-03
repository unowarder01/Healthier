package unowarder01.healthier.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val HealthierLightColorScheme = lightColorScheme(
    primary = Color(0xFF17171B),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFF0F1F6),
    onPrimaryContainer = Color(0xFF17171B),
    secondary = Color(0xFF1264D2),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFE8F2FF),
    onSecondaryContainer = Color(0xFF123A63),
    tertiary = Color(0xFF168A68),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFD8F3E9),
    onTertiaryContainer = Color(0xFF074E3B),
    error = Color(0xFFD92D20),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFEE4E2),
    onErrorContainer = Color(0xFF7A271A),
    background = Color(0xFFF7F7FA),
    onBackground = Color(0xFF17171B),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF17171B),
    surfaceVariant = Color(0xFFF0F1F6),
    onSurfaceVariant = Color(0xFF626272),
    outline = Color(0xFFD2D4DE),
    outlineVariant = Color(0xFFE1E2E9),
    inverseSurface = Color(0xFF17171B),
    inverseOnSurface = Color(0xFFFFFFFF),
    inversePrimary = Color(0xFF79B8FF),
    scrim = Color(0xFF17171B),
    surfaceTint = Color.Transparent
)

private val HealthierDarkColorScheme = darkColorScheme(
    primary = Color(0xFFF7F7FA),
    onPrimary = Color(0xFF17171B),
    primaryContainer = Color(0xFF2A2A32),
    onPrimaryContainer = Color(0xFFF7F7FA),
    secondary = Color(0xFF79B8FF),
    onSecondary = Color(0xFF17171B),
    secondaryContainer = Color(0xFF173A5E),
    onSecondaryContainer = Color(0xFFD8EAFF),
    tertiary = Color(0xFF49B990),
    onTertiary = Color(0xFF062F25),
    tertiaryContainer = Color(0xFF0F5F48),
    onTertiaryContainer = Color(0xFFD8F3E9),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF0E0E12),
    onBackground = Color(0xFFF7F7FA),
    surface = Color(0xFF18181E),
    onSurface = Color(0xFFF7F7FA),
    surfaceVariant = Color(0xFF22222A),
    onSurfaceVariant = Color(0xFFB8B8C4),
    outline = Color(0xFF393943),
    outlineVariant = Color(0xFF2A2A32),
    inverseSurface = Color(0xFFF7F7FA),
    inverseOnSurface = Color(0xFF17171B),
    inversePrimary = Color(0xFF1264D2),
    scrim = Color(0xFF000000),
    surfaceTint = Color.Transparent
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

@Composable
fun HealthierTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) HealthierDarkColorScheme else HealthierLightColorScheme,
        typography = HealthierTypography,
        shapes = HealthierShapes,
        content = content
    )
}
