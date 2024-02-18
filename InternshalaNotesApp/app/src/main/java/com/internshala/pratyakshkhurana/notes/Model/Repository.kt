package com.internshala.pratyakshkhurana.notes.Model

import androidx.lifecycle.LiveData

class Repository(private val dao: NotesDao) {

    fun getAllNotes(searchQuery: String): LiveData<List<NotesEntity>> {
        return dao.getAllNotes(searchQuery)
    }

    fun insertNote(note: NotesEntity) {
        dao.insertNote(note)
    }

    fun deleteNote(note: NotesEntity) {
        dao.deleteNote(note)
    }

    fun update(note: NotesEntity) {
        dao.updateNote(note)
    }
}
