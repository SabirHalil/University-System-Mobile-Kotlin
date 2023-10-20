package com.example.universitysystem.network

import com.example.universitysystem.data.models.ChangePasswordBody
import com.example.universitysystem.data.models.LoginBody
import com.example.universitysystem.data.models.TokenResponse
import retrofit2.Call
import retrofit2.http.*

interface AuthApi {

    @POST("auth/Login")
    fun getToken(@Body body: LoginBody): Call<TokenResponse>

    @POST("auth/Login/change_password")
    fun changePassword(@Body body : ChangePasswordBody) : Call<Void>

}