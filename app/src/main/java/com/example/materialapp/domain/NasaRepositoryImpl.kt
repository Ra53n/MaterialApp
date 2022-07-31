package com.example.materialapp.domain

import com.example.materialapp.BuildConfig
import com.example.materialapp.api.NasaApi
import com.example.materialapp.domain.mappers.MarsRoverPhotoResponseToEntityMapper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NasaRepositoryImpl : NasaRepository {

    private val marsRoverPhotoMapper = MarsRoverPhotoResponseToEntityMapper()

    private val api = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.nasa.gov/")
        .build()
        .create(NasaApi::class.java)

    override suspend fun pictureOfTheDay(date: String) =
        api.getPictureOfTheDay(
            BuildConfig.NASA_API_KEY,
            date
        )

    override suspend fun marsRoverPhotos(cameraName: CameraName) =
        marsRoverPhotoMapper.map(
            api.getMarsPhotos(BuildConfig.NASA_API_KEY, 3000)
        )

}