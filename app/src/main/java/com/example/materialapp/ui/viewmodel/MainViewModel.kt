package com.example.materialapp.ui.viewmodel

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.materialapp.domain.data.PictureOfTheDayEntity
import com.example.materialapp.domain.repos.NasaRepository
import com.example.materialapp.ui.view.fragment.DescriptionBottomSheetFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel(private val repository: NasaRepository) : ViewModel() {

    private val _image: MutableStateFlow<String?> = MutableStateFlow(null)
    val image: Flow<String?> = _image

    private val _title: MutableStateFlow<String?> = MutableStateFlow(null)
    val title: Flow<String?> = _title

    private var currentPictureEntity: PictureOfTheDayEntity? = null

    fun requestPictureOfTheDay(isYesterday: Boolean) {
        viewModelScope.launch {
            try {
                currentPictureEntity = repository.pictureOfTheDay(getDateForRequest(isYesterday))
                currentPictureEntity?.let {
                    _image.emit(it.url)
                    _title.emit(it.title)
                }
            } catch (exception: Exception) {
                Log.e("@@@", exception.message.toString())
            }

        }
    }

    private fun getDateForRequest(isYesterday: Boolean): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
        val date = Calendar.getInstance()
        if (isYesterday) {
            date.add(Calendar.DAY_OF_YEAR, -1)
        }
        return dateFormat.format(date)
    }

    fun onImageClick(manager: FragmentManager) {
        currentPictureEntity?.let {
            DescriptionBottomSheetFragment(it).show(manager, "")
        }
    }
}

class MainViewModelFactory(private val repository: NasaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) = MainViewModel(repository) as T
}