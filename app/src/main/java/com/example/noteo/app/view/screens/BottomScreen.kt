package com.example.noteo.app.view.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.noteo.R

sealed class BottomScreen(
    @StringRes
    val bTitle: Int,
    val bRoute: String,
    @DrawableRes
    val icon: Int
) : Screen(bTitle, bRoute) {

    object NotesListScreen : BottomScreen(
        R.string.screen_name_notes_list,
        "notes_list_screen",
        R.drawable.baseline_note_alt_24
    )

    object ToDoScreen : BottomScreen(
        R.string.screen_name_todo_list,
        "to_do_screen",
        R.drawable.baseline_task_alt_24
    )
}

val screensInBottom = listOf(
    BottomScreen.NotesListScreen,
    BottomScreen.ToDoScreen
)

