package com.example.materialapp.domain.data.notesDB

import androidx.room.*

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: NoteEntity)

    @Delete
    fun delete(entity: NoteEntity)

    @Update
    fun update(entity: NoteEntity)

    @Query("SELECT * FROM note_items_entity")
    fun getAllNotes(): List<NoteEntity>
}