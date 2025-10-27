package com.example.noteo.app.model.task

import kotlinx.coroutines.flow.Flow

class TasksRepository(
    private val tasksDao: TasksDao
) {
    suspend fun addTask(task: Task) {
        tasksDao.addTask(task)
    }

    suspend fun updateTask(task: Task) {
        tasksDao.updateTask(task)
    }

    fun getAllTasks(): Flow<List<Task>> {
        return tasksDao.getAllTasks()
    }

    fun getTaskById(id: Long): Flow<Task> {
        return tasksDao.getTaskById(id)
    }

    suspend fun deleteTask(task: Task) {
        tasksDao.deleteTask(task)
    }
}
