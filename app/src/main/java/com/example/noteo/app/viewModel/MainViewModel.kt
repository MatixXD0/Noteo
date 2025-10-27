package com.example.noteo.app.viewModel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteo.app.view.screens.BottomScreen
import com.example.noteo.app.view.screens.Screen
import com.example.noteo.ui.theme.ThemePreference
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _currentScreen: MutableState<Screen> =
        mutableStateOf(BottomScreen.NotesListScreen)

    val currentScreen: MutableState<Screen>
        get() = _currentScreen


    enum class Theme { LIGHT, DARK }

    private val themePreference = ThemePreference(application)

    private val _currentTheme = mutableStateOf(
        when (themePreference.getTheme()) {
            ThemePreference.THEME_DARK -> Theme.DARK
            else -> Theme.LIGHT
        }
    )
    val currentTheme = _currentTheme

    fun setTheme(theme: Theme) {
        _currentTheme.value = theme
        saveTheme(theme)
    }

    private fun saveTheme(theme: Theme) {
        viewModelScope.launch {
            themePreference.saveTheme(
                when (theme) {
                    Theme.LIGHT -> ThemePreference.THEME_LIGHT
                    Theme.DARK -> ThemePreference.THEME_DARK
                }
            )
        }
    }
}

