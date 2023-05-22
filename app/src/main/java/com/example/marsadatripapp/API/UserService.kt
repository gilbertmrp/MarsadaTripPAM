package com.example.marsadatripapp.API

import com.example.marsadatripapp.model.ResponseModel
import com.example.marsadatripapp.model.User
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseModel>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<ResponseModel>

    @FormUrlEncoded
    @POST("update/{id}")
    fun update(
        @Path("id") id: Int,
        @Field("name") name: String,
        @Field("email") email: String,
    ) : Call<ResponseModel>

    @FormUrlEncoded
    @POST("update/pass/{id}")
    fun updatePass(
        @Path("id") id: Int,
        @Field("password") password: String,
    ) : Call<ResponseModel>

    @POST("logout")
    fun logout(@Header("Authorization") token: String) : Call<Void>
}