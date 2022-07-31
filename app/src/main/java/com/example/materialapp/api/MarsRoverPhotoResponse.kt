package com.example.materialapp.api

import com.google.gson.annotations.SerializedName

data class MarsRoverPhotoResponse(
    val photos: List<Photo>
)

data class Photo(
    val id: Long,
    val sol: Long,
    val camera: Camera,

    @SerializedName("img_src")
    val imgSrc: String,

    @SerializedName("earth_date")
    val earthDate: String,
)

data class Camera(
    val id: Long,
    val name: String?,

    @SerializedName("rover_id")
    val roverID: Long
)
