package com.example.materialapp.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(
        @Query("api_key") key: String,
        @Query("date") date: String
    ): PictureOfTheDayResponse

    @GET("mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getMarsPhotos(
        @Query("api_key") key: String,
        @Query("sol") sol: Int
    ): MarsRoverPhotoResponse
}