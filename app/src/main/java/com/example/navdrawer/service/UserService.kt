package com.example.navdrawer.service

import com.example.navdrawer.model.User
import com.example.navdrawer.model.UserResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    companion object {

        val instance = Retrofit.Builder().baseUrl("https://api-android2023-klhg-dev.fl0.io/users/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }

    @POST("register")
    suspend fun insertUser(@Body user: User): UserResponse
}