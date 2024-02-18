package com.internshala.pratyakshkhurana.notes.Model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {
    // for accessing the table and database

    @Query("SELECT * FROM notesTable WHERE userEmail = :searchQuery ORDER BY lastUpdatedTime DESC")
    fun getAllNotes(searchQuery: String): LiveData<List<NotesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: NotesEntity)

    @Delete
    fun deleteNote(note: NotesEntity)

    @Update
    fun updateNote(note: NotesEntity)
}
