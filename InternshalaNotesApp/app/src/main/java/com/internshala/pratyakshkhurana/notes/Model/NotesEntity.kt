package com.internshala.pratyakshkhurana.notes.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notesTable")
// table formed in SQLite
data class NotesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val title: String,
    val description: String?,
    val lastUpdatedTime: String,
    val lastUpdatedDate: String,
    val requiredDateFormat: String,
    val userEmail: String
)
