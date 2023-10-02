package com.example.navdrawer.service

import com.example.navdrawer.model.AddFavoriteOrganizationResponse
import com.example.navdrawer.model.UserLogin
import com.example.navdrawer.model.UserLoginResponse
import com.example.navdrawer.model.UserProtectedResponse
import com.example.navdrawer.model.UserRegister
import com.example.navdrawer.model.UserRegistrationResponse
import com.example.navdrawer.model.GetUserFavoriteOrganizationsResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    companion object {

        val instance: UserService =
            Retrofit.Builder().baseUrl("https://api-android2023-klhg-dev.fl0.io/users/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserService::class.java)
    }

    @POST("register")
    suspend fun insertUser(@Body user: UserRegister): UserRegistrationResponse

    @POST("login")
    suspend fun loginUser(@Body user: UserLogin): UserLoginResponse

    @GET("protected")
    //@Headers("Authorization: {token}")
    suspend fun protectedRoute(@Header("Authorization") token: String): UserProtectedResponse

    @POST("add-favorite/{organizationId}")
    suspend fun addFavoriteOrganization(
        @Header("Authorization") token: String,
        @Path("organizationId") organizationId: String
    ): AddFavoriteOrganizationResponse


    @GET("getUserFavoriteOrganizations/")
    suspend fun getUserFavoriteOrganization(
        @Header("Authorization") token: String
    ): GetUserFavoriteOrganizationsResponse


}