package com.example.materialapp.domain.repos

import android.content.Context
import androidx.room.Room
import com.example.materialapp.domain.data.notesDB.NoteEntity
import com.example.materialapp.domain.data.notesDB.NotesDatabase

const val NOTES_ITEMS_DATABASE = "NOTES_ITEMS_DATABASE"

class NoteRepositoryImpl(private val context: Context) : NoteRepository {

    private val database = Room.databaseBuilder(
        context, NotesDatabase::class.java,
        NOTES_ITEMS_DATABASE
    ).build()

    override suspend fun getAllNotes() = database.noteItemsDao().getAllNotes()

    override suspend fun deleteNote(note: NoteEntity) {
        database.noteItemsDao().delete(note)
    }

    override suspend fun updateNote(note: NoteEntity) {
        database.noteItemsDao().update(note)
    }

    override suspend fun insertNote(note: NoteEntity) {
        database.noteItemsDao().insert(note)
    }
}