package com.example.materialapp.domain

import com.example.materialapp.api.PictureOfTheDayResponse

interface NasaRepository {

    suspend fun pictureOfTheDay(date: String): PictureOfTheDayResponse

    suspend fun marsRoverPhotos(cameraName: CameraName): List<MarsRoverPhotoEntity>

}