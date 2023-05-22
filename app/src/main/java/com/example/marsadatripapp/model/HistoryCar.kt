package com.example.marsadatripapp.model

data class HistoryCar(
    val success: Int,
    val message: String,
    val data: ArrayList<Detail>
)

data class Detail(
    val id: Int,
    val user_id: Int,
    val status: String,
    val car_id: Int,
    val created_at: String,
    val updated_at: String,
    val user: User,
    val car: Car,
)
