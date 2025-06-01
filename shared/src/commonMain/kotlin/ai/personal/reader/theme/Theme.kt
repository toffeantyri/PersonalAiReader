package ai.personal.reader.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

private val LightColorScheme = lightColorScheme(

//    primary = DarkBrown,
//    onPrimary = SmokyWhite,
//
//    secondary = Orange,
//    onSecondary = White,
//
//    background = SmokyWhite,
//    onBackground = DarkBrown,
//
//    surface = White,
//    onSurface = DarkBrown,
//
//    surfaceTint = White,
//
//    error = RedAttention,
//    onError = White,

)

private val DarkColorScheme = darkColorScheme(

//    primary = DarkBrown, //main text const
//    onPrimary = SmokyWhite, //const
//
//    secondary = Orange, //const
//    onSecondary = White,  //const
//
//    background = DarkBrown, //main background changeable
//    onBackground = SmokyWhite, //changeable
//
//    surface = DarkBrown, // main surface changeable
//    onSurface = SmokyWhite, // changeable
//
//    error = RedAttention,
//    onError = White,
//
)

@Stable
@Composable
private fun getTypography(): Typography {
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
internal fun AppTheme(
    content: @Composable() () -> Unit
) {
    val systemIsDark = isSystemInDarkTheme()
    val isDarkState = remember { mutableStateOf(false) }
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
