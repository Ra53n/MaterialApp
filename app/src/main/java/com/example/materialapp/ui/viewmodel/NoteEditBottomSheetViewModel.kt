package com.example.materialapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.materialapp.domain.data.notesDB.NoteEntity
import com.example.materialapp.domain.repos.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteEditBottomSheetViewModel(private val repository: NoteRepository) : ViewModel() {

    fun updateNote(note: NoteEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.updateNote(note)
            } catch (exception: Exception) {
                Log.e("@@@", exception.message.toString())
            }
        }
    }
}

class NoteEditViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        NoteEditBottomSheetViewModel(repository) as T
}