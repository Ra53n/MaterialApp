package com.example.materialapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.materialapp.domain.mappers.NoteMapper
import com.example.materialapp.domain.repos.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteBottomSheetViewModel(private val repository: NoteRepository) : ViewModel() {

    private val mapper = NoteMapper()

    fun insertNote(title: String, text: String, priority: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.insertNote(mapper.map(title = title, text = text, priority = priority))
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