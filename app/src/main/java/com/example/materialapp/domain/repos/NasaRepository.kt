package com.example.materialapp.domain.repos

import com.example.materialapp.domain.data.MarsRoverPhotoEntity
import com.example.materialapp.domain.data.PictureOfTheDayEntity

interface NasaRepository {

    suspend fun pictureOfTheDay(date: String): PictureOfTheDayEntity

    suspend fun marsRoverPhotos(cameraName: String): List<MarsRoverPhotoEntity>

}