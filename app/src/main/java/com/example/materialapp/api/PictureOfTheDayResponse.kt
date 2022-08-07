package com.example.materialapp.api

import com.google.gson.annotations.SerializedName
import java.util.*

data class PictureOfTheDayResponse(

    @SerializedName("date")
    val date: Date,

    @SerializedName("explanation")
    val explanation: String,

    @SerializedName("hdurl")
    val hdurl: String,

    @SerializedName("media_type")
    val mediaType: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("url")
    val url: String
)
