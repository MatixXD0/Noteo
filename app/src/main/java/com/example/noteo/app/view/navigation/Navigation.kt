package com.example.noteo.app.view.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.noteo.app.view.bottomSheet.SettingsScreen
import com.example.noteo.app.view.bottomView.notes.NoteAddEditView
import com.example.noteo.app.view.bottomView.notes.NoteListView
import com.example.noteo.app.view.bottomView.toDo.TaskAddEditView
import com.example.noteo.app.view.bottomView.toDo.ToDoListView
import com.example.noteo.app.view.drawer.AboutApp
import com.example.noteo.app.view.drawer.Contact
import com.example.noteo.app.view.screens.BottomScreen
import com.example.noteo.app.view.screens.DrawerScreen
import com.example.noteo.app.view.screens.Screen
import com.example.noteo.app.viewModel.MainViewModel
import com.example.noteo.app.viewModel.NoteViewModel


@Composable
fun Navigation(
    navController: NavController,
    viewModel: MainViewModel,
    pd: PaddingValues,
    noteViewModel: NoteViewModel = viewModel()
) {

    NavHost(
        navController = navController as NavHostController,
        startDestination = BottomScreen.NotesListScreen.route,
        modifier = Modifier.padding(pd)
    ) {

        composable(BottomScreen.ToDoScreen.route) {
            ToDoListView(navController, taskViewModel = viewModel())
        }

        composable(
            Screen.TaskAddEditScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = 0L
                })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id") ?: 0L
            TaskAddEditView(
                id = id,
                taskViewModel = viewModel(),
                navController = navController
            )
        }

        composable(BottomScreen.NotesListScreen.route) {
            NoteListView(navController, noteViewModel)
        }

        composable(
            Screen.NoteAddEditScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                })
        ) { entry ->
            val id = if (entry.arguments != null)
                entry.arguments!!.getLong("id")
            else
                0L

            NoteAddEditView(
                id = id,
                viewModel = noteViewModel,
                navController = navController
            )
        }

        composable(Screen.SettingsScreen.route) {
            SettingsScreen(viewModel = viewModel)
        }

        composable(DrawerScreen.AboutAppScreen.route) {
            AboutApp()
        }

        composable(DrawerScreen.ContactScreen.route) {
            Contact()
        }
    }
}