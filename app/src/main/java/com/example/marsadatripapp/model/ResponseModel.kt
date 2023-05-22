package com.example.marsadatripapp.model

data class ResponseModel(
    val success: Int,
    val message: String,
    val data: User,
)