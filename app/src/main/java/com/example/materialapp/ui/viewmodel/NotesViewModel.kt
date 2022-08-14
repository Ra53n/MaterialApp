package com.example.materialapp.ui.viewmodel

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.materialapp.domain.data.notesDB.NoteEntity
import com.example.materialapp.domain.repos.NoteRepository
import com.example.materialapp.ui.view.fragment.AddNoteBottomSheetFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NotesViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    private val _notes: MutableStateFlow<List<NoteEntity>?> = MutableStateFlow(null)
    val notes: Flow<List<NoteEntity>?> = _notes

    fun requestNotes() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val list = noteRepository.getAllNotes()
                _notes.emit(list)
            } catch (exception: Exception) {
                Log.e("@@@", exception.message.toString())
            }
        }
    }

    fun onAddClick(manager: FragmentManager) {
        AddNoteBottomSheetFragment(noteRepository).show(manager, "")
    }

    fun removeItem(item: NoteEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            noteRepository.deleteNote(item)
        }
    }
}

class NotesViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) = NotesViewModel(repository) as T
}