package com.example.materialapp.domain.mappers

import com.example.materialapp.api.PictureOfTheDayResponse
import com.example.materialapp.domain.data.PictureOfTheDayEntity

class PictureOfTheDateResponseToEntityMapper {
    fun map(response: PictureOfTheDayResponse): PictureOfTheDayEntity {
        with(response) {
            return PictureOfTheDayEntity(date, explanation, title, url)
        }
    }
}