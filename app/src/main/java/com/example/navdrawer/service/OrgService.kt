package com.example.navdrawer.service

import com.example.navdrawer.model.OrgRegister
import com.example.navdrawer.model.OrgRegisterResponse
import com.example.navdrawer.model.UserLogin
import com.example.navdrawer.model.UserLoginResponse
import com.example.navdrawer.model.UserProtectedResponse
import com.example.navdrawer.model.UserRegister
import com.example.navdrawer.model.UserRegistrationResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface OrgService {

    companion object {

        val instance: OrgService = Retrofit.Builder().baseUrl("https://api-android2023-klhg-dev.fl0.io/orgs/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OrgService::class.java)
    }

    @POST("add")
    suspend fun addOrg(@Header("Authorization") token: String, @Body org: OrgRegister) : OrgRegisterResponse

}