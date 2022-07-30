package com.example.materialapp.domain

import com.example.materialapp.api.PictureOfTheDayResponse

interface NasaRepository {

    suspend fun pictureOfTheDay(): PictureOfTheDayResponse
}