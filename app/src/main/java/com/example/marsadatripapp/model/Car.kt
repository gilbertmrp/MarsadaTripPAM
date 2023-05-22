package com.example.marsadatripapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    val id: Int,
    val merk: String,
    val jenis: String,
    val lokasi: String,
    val jlh_penumpang: String,
    val cooler: String,
    val transmisi: String,
    val jlh_pintu: String,
) : Parcelable

data class Data(
    val data:List<Car>
)

