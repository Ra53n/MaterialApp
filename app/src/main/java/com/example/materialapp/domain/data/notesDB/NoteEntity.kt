package com.example.materialapp.domain.data.notesDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_items_entity")
data class NoteEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val note: String,
    val date: String,
    val priority: Int
)