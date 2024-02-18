package com.internshala.pratyakshkhurana.notes.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.internshala.pratyakshkhurana.notes.Model.NotesDatabase
import com.internshala.pratyakshkhurana.notes.Model.NotesEntity
import com.internshala.pratyakshkhurana.notes.Model.Repository

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: Repository
    private lateinit var allNotes: LiveData<List<NotesEntity>>

    init {
        val dao = NotesDatabase.getDatabase(application).notesDAO
        repository = Repository(dao)
    }

    fun insertNote(note: NotesEntity) {
        repository.insertNote(note)
    }

    fun deleteNote(note: NotesEntity) {
        repository.deleteNote(note)
    }

    fun getAllNotes(searchQuery: String): LiveData<List<NotesEntity>> {
        allNotes = repository.getAllNotes(searchQuery)
        return repository.getAllNotes(searchQuery)
    }

    fun updateNote(note: NotesEntity) {
        repository.update(note)
    }
}
