package com.example.materialapp.ui.viewmodel

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.materialapp.domain.data.notesDB.NoteEntity
import com.example.materialapp.domain.repos.NoteRepository
import com.example.materialapp.ui.view.fragment.AddNoteBottomSheetFragment
import com.example.materialapp.ui.view.fragment.NoteEditBottomSheetFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NotesViewModel(private val noteRepository: NoteRepository) : ViewModel() {
    private val _notes: MutableStateFlow<List<NoteEntity>?> = MutableStateFlow(null)
    val notes: Flow<List<NoteEntity>?> = _notes

    fun requestNotes() {
        viewModelScope.launch(Dispatchers.IO) {
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
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(item)
        }
    }

    fun replaceItems(firstItem: NoteEntity, secondItem: NoteEntity) {
        val firstItemId = firstItem.id
        val secondItemId = secondItem.id
        viewModelScope.launch(Dispatchers.IO) {
            try {
                noteRepository.updateNote(secondItem.copy(id = firstItemId))
                noteRepository.updateNote(firstItem.copy(id = secondItemId))
            } catch (exception: Exception) {
                Log.e("@@@", exception.message.toString())
            }
        }
    }

    fun onItemClicked(note: NoteEntity, manager: FragmentManager) {
        NoteEditBottomSheetFragment(noteRepository, note).show(manager, "")
    }
}

class NotesViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) = NotesViewModel(repository) as T
}