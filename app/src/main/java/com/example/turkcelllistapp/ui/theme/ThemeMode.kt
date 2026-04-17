package com.example.turkcelllistapp.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

enum class ThemeMode { System, Light, Dark }

class ThemeModeState(initial: ThemeMode = ThemeMode.System) {
    var mode by mutableStateOf(initial)
        private set

    fun toggle() {
        mode = when (mode) {
            ThemeMode.System -> ThemeMode.Light
            ThemeMode.Light -> ThemeMode.Dark
            ThemeMode.Dark -> ThemeMode.System
        }
    }
}

val LocalThemeMode = compositionLocalOf { ThemeModeState() }
