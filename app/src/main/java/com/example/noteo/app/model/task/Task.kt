package com.example.noteo.app.model.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "task_name")
    val title: String,

    //Long (liczba milisekund).
    @ColumnInfo(name = "due_date")
    val dueDate: Long,

    @ColumnInfo(name = "is_done")
    val isDone: Boolean = false
)
