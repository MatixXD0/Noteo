package com.example.noteo.app.view.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.noteo.R
import com.example.noteo.app.view.bottomSheet.MoreBottomSheet
import com.example.noteo.app.view.navigation.Navigation
import com.example.noteo.app.view.screens.BottomScreen
import com.example.noteo.app.view.screens.DrawerScreen
import com.example.noteo.app.view.screens.Screen
import com.example.noteo.app.view.screens.screensInBottom
import com.example.noteo.app.view.screens.screensInDrawer
import com.example.noteo.app.viewModel.MainViewModel
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(viewModel: MainViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val scope: CoroutineScope = rememberCoroutineScope()

        val controller: NavController = rememberNavController()
        val navBackStackEntry by controller.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val currentScreen = remember {
            viewModel.currentScreen.value
        }

        val title = remember { mutableStateOf(R.string.app_name) }

        LaunchedEffect(currentRoute) {
            title.value = getTitleForRoute(currentRoute)
        }

        val modalSheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmValueChange = {
                it != ModalBottomSheetValue.HalfExpanded
            }
        )


        ModalBottomSheetLayout(
            sheetState = modalSheetState,
            sheetShape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
            ),
            sheetContent = {
                MoreBottomSheet(navController = controller)
            }) {
            Scaffold(
                bottomBar = {
                    BottomBar(
                        currentScreen = currentScreen,
                        currentRoute = currentRoute,
                        screensInBottom = screensInBottom,
                        controller = controller,
                    )
                },
                topBar = {
                    TopBar(
                        title = title,
                        scope = scope,
                        modalSheetState = modalSheetState,
                        scaffoldState = scaffoldState
                    )
                },
                scaffoldState = scaffoldState,

                drawerContent = {
                    DrawerContent(
                        currentRoute = currentRoute,
                        screensInDrawer = screensInDrawer,
                        controller = controller,
                        title = title,
                        scaffoldState = scaffoldState,
                        scope = scope
                    )
                }
            ) {
                Navigation(
                    navController = controller,
                    viewModel = viewModel,
                    pd = it
                )
            }
        }
    }
}

fun getTitleForRoute(route: String?): Int {
    return when {
        route?.startsWith(BottomScreen.ToDoScreen.route) == true -> R.string.screen_name_todo_list
        route?.startsWith(BottomScreen.NotesListScreen.route) == true -> R.string.screen_name_notes_list
        route?.startsWith(Screen.SettingsScreen.route) == true -> R.string.screen_name_settings
        route?.startsWith(Screen.NoteAddEditScreen.route) == true -> R.string.screen_name_note_add_edit_screen
        route?.startsWith(Screen.TaskAddEditScreen.route) == true -> R.string.screen_name_task_add_edit_screen
        route?.startsWith(DrawerScreen.AboutAppScreen.route) == true -> R.string.screen_name_about_app
        route?.startsWith(DrawerScreen.ContactScreen.route) == true -> R.string.screen_name_contact
        else -> R.string.app_name
    }
}

