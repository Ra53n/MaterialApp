package com.example.materialapp.domain.repos

import com.example.materialapp.domain.data.notesDB.NoteEntity

interface NoteRepository {
    suspend fun getAllNotes() : List<NoteEntity>

    suspend fun deleteNote(note: NoteEntity)

    suspend fun updateNote(note: NoteEntity)

    suspend fun insertNote(note: NoteEntity)
}