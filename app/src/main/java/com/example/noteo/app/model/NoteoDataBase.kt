package com.example.noteo.app.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteo.app.model.note.NotesDao
import com.example.noteo.app.model.note.Note
import com.example.noteo.app.model.task.Task
import com.example.noteo.app.model.task.TasksDao


@Database(
    entities = [
        Note::class,
        Task::class
    ],
    version = 3,
    exportSchema = false


)

abstract class NoteoDataBase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    abstract fun tasksDao(): TasksDao
}
