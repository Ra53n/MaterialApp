package com.example.materialapp.domain.mappers

import com.example.materialapp.api.MarsRoverPhotoResponse
import com.example.materialapp.domain.data.MarsRoverPhotoEntity

class MarsRoverPhotoResponseToEntityMapper {

    fun map(response: MarsRoverPhotoResponse): List<MarsRoverPhotoEntity> {
        return with(response.photos) {
            map {
                MarsRoverPhotoEntity(
                    imgSrc = it.imgSrc,
                    earthDate = it.earthDate
                )
            }
        }
    }
}