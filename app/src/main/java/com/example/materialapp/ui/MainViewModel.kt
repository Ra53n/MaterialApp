package com.example.materialapp.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.materialapp.domain.NasaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: NasaRepository) : ViewModel() {

    private val _image: MutableStateFlow<String?> = MutableStateFlow(null)
    val image: Flow<String?> = _image

    private val _title: MutableStateFlow<String?> = MutableStateFlow(null)
    val title: Flow<String?> = _title

    fun requestPictureOfTheDay(isHd: Boolean) {
        viewModelScope.launch {
            try {
                val response = repository.pictureOfTheDay()
                if (isHd) {
                    _image.emit(response.hdurl)
                } else {
                    _image.emit(response.url)
                }
                _title.emit(response.title)
            } catch (exception: Exception) {
                Log.e("@@@", exception.message.toString())
            }

        }
    }
}

class MainViewModelFactory(private val repository: NasaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) = MainViewModel(repository) as T
}