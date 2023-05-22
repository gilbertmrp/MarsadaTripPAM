package com.example.marsadatripapp.API

import com.example.marsadatripapp.model.Booked
import com.example.marsadatripapp.model.Data
import com.example.marsadatripapp.model.HistoryCar
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.Date

interface CarsService {
    @GET("mobils")
    fun getData() : Call<Data>

    @GET("transaksi/user/{id}")
    fun getHistoryCar(
        @Path("id") id: Int
    ) : Call<HistoryCar>

    @FormUrlEncoded
    @POST("book-car/{id}/{car_id}")
    fun postBookedCar(
        @Path("id") id: Int,
        @Path("car_id") car_id: Int,
        @Field("berangkat") berangkat: String,
        @Field("kembali") kembali: String,
        @Field("driver") driver: String,
    ) : Call<Booked>
}