package com.example.noteo.app.model

import android.content.Context
import androidx.room.Room
import com.example.noteo.app.model.note.NotesRepository
import com.example.noteo.app.model.task.TasksRepository

object Graph {
    lateinit var database: NoteoDataBase

    val notesRepository by lazy {
        NotesRepository(notesDao = database.notesDao())
    }

    val tasksRepository by lazy {
        TasksRepository(tasksDao = database.tasksDao())
    }

    fun provide(context: Context) {
        database = Room.databaseBuilder(
            context,
            NoteoDataBase::class.java,
            "noteo.db"
        )
            .fallbackToDestructiveMigration()//Usuwa tabele przy zmienie wersji bazy danych TODO Usunąć Po Skończeniu Developmentu
            .build()
    }
}