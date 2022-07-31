package com.example.materialapp.domain

data class MarsRoverPhotoEntity(
    //val camera: CameraName,
    val imgSrc: String,
    val earthDate: String
)

enum class CameraName(val value: String) {
    Chemcam("CHEMCAM"),
    Fhaz("FHAZ"),
    Mast("MAST"),
    Navcam("NAVCAM"),
    Rhaz("RHAZ");
}