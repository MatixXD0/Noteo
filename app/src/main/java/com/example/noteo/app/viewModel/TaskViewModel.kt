package com.example.noteo.app.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteo.app.model.Graph
import com.example.noteo.app.model.task.Task
import com.example.noteo.app.model.task.TasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TasksRepository = Graph.tasksRepository
) : ViewModel() {
    var taskName by mutableStateOf("")
    var taskDueDate by mutableLongStateOf(0L)

    lateinit var allTasks: Flow<List<Task>>

    init {
        viewModelScope.launch {
            allTasks = repository.getAllTasks()
        }
    }

    fun onNameChanged(newName: String) {
        taskName = newName
    }

    fun onDueDateChanged(newDate: Long) {
        taskDueDate = newDate
    }

    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    fun getTaskById(id: Long): Flow<Task> {
        return repository.getTaskById(id)
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(task)
        }
    }


}
