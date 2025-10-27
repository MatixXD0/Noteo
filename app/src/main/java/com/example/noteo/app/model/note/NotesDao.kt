package com.example.noteo.app.model.note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NotesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addNote(shoppingListEntity: Note)

    @Query("SELECT * FROM `note_table`")
    abstract fun getAllNotes(): Flow<List<Note>>

    @Update
    abstract suspend fun updateNote(shoppingListEntity: Note)

    @Delete
    abstract suspend fun deleteNote(shoppingListEntity: Note)

    @Query("SELECT * FROM `note_table` WHERE id=:id")
    abstract fun getNoteById(id: Long): Flow<Note>
}

