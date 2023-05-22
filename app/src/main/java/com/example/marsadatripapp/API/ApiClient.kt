package com.example.marsadatripapp.API

import com.example.marsadatripapp.model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL= "http://10.0.2.2:8000/api/"

    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun getCars() = retrofit.create(CarsService::class.java)
    fun getHistoryCars() = retrofit.create(CarsService::class.java)
    fun bookedCar() = retrofit.create(CarsService::class.java)

    fun createUser() = retrofit.create(UserService::class.java)
    fun loginUser() = retrofit.create(UserService::class.java)
    fun updateUser() = retrofit.create(UserService::class.java)
    fun updatePass() = retrofit.create(UserService::class.java)
    fun logout() = retrofit.create(UserService::class.java)
}