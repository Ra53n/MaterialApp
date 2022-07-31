package com.example.materialapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.materialapp.domain.CameraName
import com.example.materialapp.domain.MarsRoverPhotoEntity
import com.example.materialapp.domain.NasaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MarsViewModel(private val repository: NasaRepository) : ViewModel() {

    private val _image: MutableStateFlow<List<MarsRoverPhotoEntity>?> = MutableStateFlow(null)
    val image: Flow<List<MarsRoverPhotoEntity>?> = _image


    fun requestMarsPhotos() {
        viewModelScope.launch {
            try {
                val list = repository.marsRoverPhotos(CameraName.Mast)
                _image.emit(list)
            } catch (exception: Exception) {
                Log.e("@@@", exception.message.toString())
            }

        }
    }
}

class MarsViewModelFactory(private val repository: NasaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) = MarsViewModel(repository) as T
}