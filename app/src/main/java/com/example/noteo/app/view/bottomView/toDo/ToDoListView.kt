package com.example.noteo.app.view.bottomView.toDo

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.noteo.R
import com.example.noteo.app.model.task.Task
import com.example.noteo.app.view.screens.Screen
import com.example.noteo.app.viewModel.TaskViewModel
import com.example.noteo.ui.theme.taskColorFuture
import com.example.noteo.ui.theme.taskColorPast
import com.example.noteo.ui.theme.taskColorToday
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ToDoListView(
    navController: NavController,
    taskViewModel: TaskViewModel = viewModel()
) {
    val tasksState = taskViewModel.allTasks.collectAsState(initial = emptyList())

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                contentColor =  MaterialTheme.colorScheme.onBackground,
                containerColor =  MaterialTheme.colorScheme.primary,
                onClick = {
                    navController.navigate(Screen.TaskAddEditScreen.route + "/0L")
                }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }

    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(tasksState.value, key = { it.id }) { task ->
                val dismissState = rememberDismissState(
                    confirmStateChange = { dismissValue ->
                        if (dismissValue == DismissValue.DismissedToEnd ||
                            dismissValue == DismissValue.DismissedToStart
                        ) {
                            taskViewModel.deleteTask(task)
                        }
                        true
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        val color by animateColorAsState(
                            if (dismissState.dismissDirection == DismissDirection.EndToStart)
                                Color.Red
                            else
                                Color.Transparent,
                            label = "swipeToDelete"
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete_icon),
                                tint = Color.White
                            )
                        }
                    },
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(0.25f) },
                    dismissContent = {
                        ToDoItemRow(
                            task = task,
                            onCheckedChange = { isChecked ->
                                taskViewModel.updateTask(task.copy(isDone = isChecked))
                            },
                            onClick = {
                                navController.navigate("task_add_edit/${task.id}")
                            }
                        )
                    }
                )
            }

        }
    }
}


@Composable
fun ToDoItemRow(
    task: Task,
    onCheckedChange: (Boolean) -> Unit,
    onClick: () -> Unit
) {
    val taskColor = remember(task) {
        deriveTaskColor(task)
    }

    Card(
        colors = CardDefaults.cardColors(taskColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Checkbox(
                checked = task.isDone,
                onCheckedChange = onCheckedChange
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                val formattedDate = remember(task.dueDate) {
                    // prosta konwersja timestamp -> "yyyy-MM-dd"
                    SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(Date(task.dueDate))
                }
                Text(
                    text = stringResource(R.string.task_deadline) + " $formattedDate",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

fun deriveTaskColor(task: Task): Color {
    if (task.isDone) {
        return Color.Gray // wykonane
    }
    // Obliczamy, czy termin upłynął, jest dzisiaj czy w przyszłości
    val now = System.currentTimeMillis()
    val dateNow = stripTime(now)
    val dateDue = stripTime(task.dueDate)

    return when {
        dateDue < dateNow -> taskColorPast
        dateDue == dateNow -> taskColorToday
        else -> taskColorFuture
    }
}

fun stripTime(timeMillis: Long): Long {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeMillis
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}
