package com.example.marsadatripapp.model

import java.util.Date

data class Booked(
    val success: Int,
    val message: String,
    val data: ListBooked
)

data class ListBooked(
    val berangkat: Date,
    val kembali: Date,
    val driver: String,
    val user_id: Int,
    val car_id: Int,
    val status: Int,
)
