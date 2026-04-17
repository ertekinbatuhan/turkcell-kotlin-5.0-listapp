package com.example.turkcelllistapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Blue80,
    onPrimary = OnBlue80,
    primaryContainer = BlueContainer80,
    onPrimaryContainer = OnBlueContainer80,
    secondary = BlueGrey80,
    secondaryContainer = BlueGreyContainer80,
    tertiary = Teal80,
    tertiaryContainer = TealContainer80
)

private val LightColorScheme = lightColorScheme(
    primary = Blue40,
    onPrimary = OnBlue40,
    primaryContainer = BlueContainer40,
    onPrimaryContainer = OnBlueContainer40,
    secondary = BlueGrey40,
    secondaryContainer = BlueGreyContainer40,
    tertiary = Teal40,
    tertiaryContainer = TealContainer40
)

@Composable
fun TurkcellListAppTheme(
    themeModeState: ThemeModeState = remember { ThemeModeState() },
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeModeState.mode) {
        ThemeMode.System -> isSystemInDarkTheme()
        ThemeMode.Light -> false
        ThemeMode.Dark -> true
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(LocalThemeMode provides themeModeState) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
