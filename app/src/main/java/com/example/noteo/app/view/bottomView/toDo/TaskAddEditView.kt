package com.example.noteo.app.view.bottomView.toDo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.noteo.R
import com.example.noteo.app.model.task.Task
import com.example.noteo.app.viewModel.TaskViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskAddEditView(
    id: Long,
    taskViewModel: TaskViewModel,
    navController: NavController
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val scrollState = rememberScrollState()


    // Jeżeli wchodzimy w tryb edycji (id != 0), to pobieramy zadanie:
    if (id != 0L) {
        val taskFlow = taskViewModel.getTaskById(id)
        LaunchedEffect(id) {
            taskFlow.collect {
                taskViewModel.taskName = it.title
                taskViewModel.taskDueDate = it.dueDate
            }
        }
    } else {
        // Nowe zadanie: zerujemy stany
        SideEffect {
            taskViewModel.taskName = ""
            taskViewModel.taskDueDate = 0L
        }
    }

    // Jeżeli w VM mamy 0L, weźmy domyślnie dzisiejszą datę:
    val defaultDate = if (taskViewModel.taskDueDate == 0L) {
        System.currentTimeMillis()
    } else {
        taskViewModel.taskDueDate
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = defaultDate
    )

    Scaffold(
        snackbarHost = { androidx.compose.material3.SnackbarHost(hostState = snackBarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
            .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,

        ) {
            OutlinedTextField(
                value = taskViewModel.taskName,
                onValueChange = { taskViewModel.onNameChanged(it) },
                label = { Text(text = stringResource(R.string.task_name)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            DatePicker(state = datePickerState)

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                modifier = Modifier
                    .padding(4.dp)
                    .align(alignment = Alignment.CenterHorizontally),
                onClick = {
                    // Po zatwierdzeniu datePickera:
                    val selectedMillis = datePickerState.selectedDateMillis
                    if (taskViewModel.taskName.isBlank()) {
                        scope.launch {
                            snackBarHostState.showSnackbar(context.getString(R.string.task_snack_name_the_task))
                        }
                        return@Button
                    }
                    if (selectedMillis == null) {
                        scope.launch {
                            snackBarHostState.showSnackbar(context.getString(R.string.task_snack_pick_a_date))
                        }
                        return@Button
                    }
                    // Zapis/aktualizacja w bazie:
                    if (id == 0L) {
                        // Nowe zadanie
                        taskViewModel.addTask(
                            Task(
                                title = taskViewModel.taskName.trim(),
                                dueDate = selectedMillis,
                                isDone = false
                            )
                        )
                    } else {
                        // Aktualizacja
                        taskViewModel.updateTask(
                            Task(
                                id = id,
                                title = taskViewModel.taskName.trim(),
                                dueDate = selectedMillis,
                                isDone = false //bez sensu edytować zadanie które jest zrobione, dlatego zmienaimy status
                            )
                        )
                    }
                    navController.navigateUp()
                }
            ) {
                Text(color = MaterialTheme.colorScheme.onBackground,
                    text = if (id != 0L)
                    stringResource(R.string.task_update_button)
                else stringResource(
                    R.string.task_add_button
                )
                )
            }
        }
    }
}
