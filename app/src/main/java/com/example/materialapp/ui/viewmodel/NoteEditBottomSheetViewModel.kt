package com.example.materialapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.materialapp.domain.data.notesDB.NoteEntity
import com.example.materialapp.domain.interactors.NotesInteractor
import com.example.materialapp.domain.mappers.NoteMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteEditBottomSheetViewModel(
    private val interactor: NotesInteractor,
    private val mapper: NoteMapper
) : ViewModel() {

    fun onUpdateNote(id: String, title: String, text: String, priority: String) {
        updateNote(mapper.map(id, title, text, priority))
    }

    private fun updateNote(noteEntity: NoteEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                interactor.updateNote(noteEntity)
            } catch (exception: Exception) {
                Log.e("@@@", exception.message.toString())
            }
        }
    }
}

class NoteEditViewModelFactory(
    private val interactor: NotesInteractor,
    private val mapper: NoteMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        NoteEditBottomSheetViewModel(interactor, mapper) as T
}