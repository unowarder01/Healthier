package unowarder01.healthier.core.designsystem

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
import unowarder01.healthier.core.common.AppTheme

object HealthierTokens {
    val accent = Color(0xFF0B806A)
    val meta = Color(0xFF0866FF)
    val telegram = Color(0xFF229ED9)
    val radius = 16.dp
    val floatingElevation = 8.dp
    val pageHorizontalPadding = 20.dp
    val sectionSpacing = 28.dp
    val itemSpacing = 12.dp
}

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF007A67),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB8F4DD),
    onPrimaryContainer = Color(0xFF002117),
    secondary = Color(0xFF3558C9),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFDEE1FF),
    onSecondaryContainer = Color(0xFF001452),
    tertiary = Color(0xFF805500),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFDDB4),
    onTertiaryContainer = Color(0xFF291800),
    error = Color(0xFFBA1A1A),
    onError = Color.White,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFF8FAF7),
    onBackground = Color(0xFF191C1A),
    surface = Color(0xFFF8FAF7),
    onSurface = Color(0xFF191C1A),
    surfaceVariant = Color(0xFFDCE5DF),
    onSurfaceVariant = Color(0xFF404944),
    outline = Color(0xFF707974),
    outlineVariant = Color(0xFFC0C9C3)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF93D7C0),
    onPrimary = Color(0xFF00382C),
    primaryContainer = Color(0xFF00513F),
    onPrimaryContainer = Color(0xFFB0F4DB),
    secondary = Color(0xFFBCC3FF),
    onSecondary = Color(0xFF001A74),
    secondaryContainer = Color(0xFF1C3DAF),
    onSecondaryContainer = Color(0xFFDEE1FF),
    tertiary = Color(0xFFFFB95E),
    onTertiary = Color(0xFF442B00),
    tertiaryContainer = Color(0xFF624000),
    onTertiaryContainer = Color(0xFFFFDDB4),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF101412),
    onBackground = Color(0xFFE0E3DF),
    surface = Color(0xFF101412),
    onSurface = Color(0xFFE0E3DF),
    surfaceVariant = Color(0xFF404944),
    onSurfaceVariant = Color(0xFFC0C9C3),
    outline = Color(0xFF8A938E),
    outlineVariant = Color(0xFF404944)
)

private val HealthierTypography = Typography(
    displaySmall = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 42.sp,
        letterSpacing = (-0.5).sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 36.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 30.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 26.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 22.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 23.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)

private val HealthierShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

@Composable
fun HealthierTheme(
    theme: AppTheme,
    systemDark: Boolean,
    content: @Composable () -> Unit
) {
    val isDark = when (theme) {
        AppTheme.System -> systemDark
        AppTheme.Light -> false
        AppTheme.Dark -> true
    }

    MaterialTheme(
        colorScheme = if (isDark) DarkColorScheme else LightColorScheme,
        typography = HealthierTypography,
        shapes = HealthierShapes,
        content = content
    )
}
