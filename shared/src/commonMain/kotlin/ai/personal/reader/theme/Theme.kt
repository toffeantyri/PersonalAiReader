package ai.personal.reader.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

// Common colors
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)

// Define custom colors for Light Theme
val LightPurple = Color(0xFF6200EE) // A deep purple
val LightTeal = Color(0xFF03DAC5) // A vibrant teal
val Red = Color(0xFFB00020) // A standard red for errors

// Define custom colors for Dark Theme
val DarkPurple = Color(0xFFBB86FC) // A lighter purple
val DarkTeal = Color(0xFF03DAC6) // A slightly different teal
val DarkRed = Color(0xFFCF6679) // A reddish pink for errors
val DarkGrey = Color(0xFF121212) // Very dark grey

private val LightColorScheme = lightColorScheme(
    primary = LightPurple,
    onPrimary = White,
    secondary = LightTeal,
    onSecondary = Black,
    error = Red,
    onError = White,
    background = White,
    onBackground = Black,
    surface = White,
    onSurface = Black,
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPurple,
    onPrimary = Black,
    secondary = DarkTeal,
    onSecondary = Black,
    error = DarkRed,
    onError = Black,
    background = DarkGrey,
    onBackground = White,
    surface = DarkGrey,
    onSurface = White,
)

@Stable
@Composable
fun getTypography(): Typography {
    return Typography(
//        titleLarge = bold18TitleTextStyle(),
//        titleMedium = bold16TextStyle(),
//        titleSmall = bold14TextStyle(),
//
//        headlineLarge = medium18TextStyle(),
//        headlineMedium = medium16TextStyle(),
//        headlineSmall = medium14TextStyle(),
//
//        displayLarge = regular16TextStyle(),
//        displayMedium = regular14TextStyle(),
//        displaySmall = regular12TextStyle(),

    )
}


internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }

@Composable
fun AppTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val isDarkState = remember { mutableStateOf(isDarkTheme) }

    LaunchedEffect(isDarkTheme) {
        isDarkState.value = isDarkTheme
    }

    CompositionLocalProvider(
        LocalThemeIsDark provides isDarkState
    ) {
        val isDark by isDarkState
        SystemAppearance(!isDark)
        MaterialTheme(
            colorScheme = if (isDark) DarkColorScheme else LightColorScheme,
            content = content,
            typography = getTypography()
        )
    }
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)
