package com.example.materialapp.domain.interactors

import com.example.materialapp.domain.data.notesDB.NoteEntity
import com.example.materialapp.domain.repos.NoteRepository

class NotesInteractor(private val repository: NoteRepository) {

    suspend fun getAllNotes(): List<NoteEntity> {
        return repository.getAllNotes()
    }

    suspend fun deleteNote(note: NoteEntity) {
        repository.deleteNote(note)
    }

    suspend fun updateNote(note: NoteEntity) {
        repository.updateNote(note)
    }

    suspend fun insertNote(note: NoteEntity) {
        repository.insertNote(note)
    }

    suspend fun swapNotes(firstNote: NoteEntity, secondNote: NoteEntity) {
        repository.updateNote(secondNote.copy(id = firstNote.id))
        repository.updateNote(firstNote.copy(id = secondNote.id))
    }

}