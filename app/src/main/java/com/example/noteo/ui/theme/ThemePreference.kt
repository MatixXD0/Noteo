package com.example.noteo.ui.theme

import android.content.Context
import android.content.SharedPreferences

class ThemePreference(context: Context) {

    companion object {
        private const val PREFS_NAME = "theme_prefs"
        private const val KEY_THEME = "current_theme"
        const val THEME_LIGHT = "light"
        const val THEME_DARK = "dark"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveTheme(theme: String) {
        prefs.edit().putString(KEY_THEME, theme).apply()
    }

    fun getTheme(): String {
        return prefs.getString(KEY_THEME, THEME_LIGHT) ?: THEME_LIGHT
    }
}
