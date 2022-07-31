package com.example.materialapp.domain.data

import java.util.*

data class PictureOfTheDayEntity(
    val date: Date,

    val explanation: String,

    val title: String,

    val url: String
)