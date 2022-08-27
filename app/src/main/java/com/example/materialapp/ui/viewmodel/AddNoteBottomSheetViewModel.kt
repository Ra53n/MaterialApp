package com.example.materialapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.materialapp.domain.data.notesDB.NoteEntity
import com.example.materialapp.domain.repos.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteBottomSheetViewModel(private val repository: NoteRepository) : ViewModel() {

    fun insertNote(note: NoteEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.insertNote(note)
            } catch (exception: Exception) {
                Log.e("@@@", exception.message.toString())
            }
        }
    }
}

class AddNoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        AddNoteBottomSheetViewModel(repository) as T
}