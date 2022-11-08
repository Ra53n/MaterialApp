package com.example.materialapp.domain.mappers

import com.example.materialapp.domain.data.notesDB.NoteEntity
import java.util.*

class NoteMapper {
    fun map(id: String = UUID.randomUUID().toString(),title: String, text: String, priority: String) = NoteEntity(
        id,
        title,
        text,
        Date(System.currentTimeMillis()).toString(),
        if (priority.isEmpty()) MIN_PRIORITY
        else priority.toInt()
    )

    companion object {
        const val MIN_PRIORITY = 1
    }
}