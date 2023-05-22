package com.example.marsadatripapp.helper

import android.content.Context
import android.content.SharedPreferences
import com.example.marsadatripapp.model.Car
import com.example.marsadatripapp.model.User
import com.google.gson.Gson

class SharedPref(context: Context) {
    private val myPref = "MAIN_PREF"
    private val sharedPref: SharedPreferences
    private val login = "login"
    val name = "nama"
    val email = "email"
    val id = "id"
    val password = "password"

    val user = "user"

    val car = "car"

    init {
        sharedPref = context.getSharedPreferences(myPref, Context.MODE_PRIVATE)
    }

    fun setStatusLogin(status: Boolean) {
        sharedPref.edit()
            .putBoolean(login, status)
            .apply()
    }

    fun getStatus(): Boolean {
        return sharedPref.getBoolean(login, false)
    }

    fun setString(key: String, value : String) {
        sharedPref.edit().putString(key, value).apply()
    }

    fun getString(key: String) : String{
        return sharedPref.getString(key, "")!!
    }

    fun setUser(value: User) {
        val data = Gson().toJson(value, User::class.java)
        sharedPref.edit().putString(user, data).apply()
    }

    fun getUser() : User? {
        val data = sharedPref.getString(user, null) ?: return null
        return Gson().fromJson<User>(data, User::class.java)
    }
}