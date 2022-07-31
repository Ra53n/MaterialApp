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
    val name: CameraName?,

    @SerializedName("rover_id")
    val roverID: Long
)

enum class CameraName(val value: String) {
    Chemcam("CHEMCAM"),
    Fhaz("FHAZ"),
    Mast("MAST"),
    Navcam("NAVCAM"),
    Rhaz("RHAZ");

    companion object {
        public fun fromValue(value: String): CameraName = when (value) {
            "CHEMCAM" -> Chemcam
            "FHAZ" -> Fhaz
            "MAST" -> Mast
            "NAVCAM" -> Navcam
            "RHAZ" -> Rhaz
            else -> throw IllegalArgumentException()
        }
    }
}
