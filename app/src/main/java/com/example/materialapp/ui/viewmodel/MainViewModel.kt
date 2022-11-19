package com.example.materialapp.ui.viewmodel

import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.materialapp.domain.data.PictureOfTheDayEntity
import com.example.materialapp.domain.repos.NasaRepository
import com.example.materialapp.ui.view.fragment.DescriptionBottomSheetFragment
import com.example.materialapp.ui.view.utils.DateFormatter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: NasaRepository,
    private val dateFormatter: DateFormatter
) : ViewModel() {

    private val _image: MutableStateFlow<String?> = MutableStateFlow(null)
    val image: Flow<String?> = _image

    private val _title: MutableStateFlow<String?> = MutableStateFlow(null)
    val title: Flow<String?> = _title

    private var currentPictureEntity: PictureOfTheDayEntity? = null

    fun requestPictureOfTheDay(isYesterday: Boolean) {
        viewModelScope.launch {
            try {
                currentPictureEntity = repository.pictureOfTheDay(dateFormatter.getDateForRequest(isYesterday))
                currentPictureEntity?.let {
                    _image.emit(it.url)
                    _title.emit(it.title)
                }
            } catch (exception: Exception) {
                Log.e("@@@", exception.message.toString())
            }
        }
    }

    fun onImageClick(manager: FragmentManager) {
        currentPictureEntity?.let {
            DescriptionBottomSheetFragment(it).show(manager, "")
        }
    }
}

class MainViewModelFactory(
    private val repository: NasaRepository,
    private val dateFormatter: DateFormatter
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        MainViewModel(repository, dateFormatter) as T
}