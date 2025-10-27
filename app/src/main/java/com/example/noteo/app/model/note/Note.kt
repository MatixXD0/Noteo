package com.example.noteo.app.model.note

import androidx.compose.ui.graphics.toArgb
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteo.ui.theme.noteColorDefault

@Entity(tableName = "note_table")
data class Note(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "note_name")
    val title: String = "",

    @ColumnInfo(name = "note_details")
    val description: String = "",

    @ColumnInfo(name = "note_color")
    val color: Int = noteColorDefault.toArgb()


)