package com.example.noteo.app.view.screens

import androidx.annotation.StringRes
import com.example.noteo.R

sealed class Screen(
    @StringRes
    val title: Int,
    val route: String
) {
    object NoteAddEditScreen : Screen(
        R.string.screen_name_note_add_edit_screen,
        "shopping_list_add_edit_item_screen",
    )

    object TaskAddEditScreen : Screen(
        R.string.screen_name_task_add_edit_screen,
        "task_add_edit",
    )

    object SettingsScreen : Screen(
        R.string.screen_name_settings,
        "settings_screen",
    )
}


