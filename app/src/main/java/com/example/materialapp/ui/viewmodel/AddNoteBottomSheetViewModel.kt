package com.example.materialapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.materialapp.domain.interactors.NotesInteractor
import com.example.materialapp.domain.mappers.NoteMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoteBottomSheetViewModel(private val interactor: NotesInteractor) : ViewModel() {

    private val mapper = NoteMapper()

    fun insertNote(title: String, text: String, priority: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                interactor.insertNote(mapper.map(title = title, text = text, priority = priority))
            } catch (exception: Exception) {
                Log.e("@@@", exception.message.toString())
            }
        }
    }
}

class AddNoteViewModelFactory(private val interactor: NotesInteractor) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        AddNoteBottomSheetViewModel(interactor) as T
}