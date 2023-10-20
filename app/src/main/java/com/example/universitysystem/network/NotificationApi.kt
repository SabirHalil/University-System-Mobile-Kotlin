package com.example.universitysystem.network

import com.example.universitysystem.data.models.Notification
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationApi {
    @GET("api/Notifications/{studentId}")
    fun getAllNotifications(@Path("studentId") id:Int): Call<ArrayList<Notification>>
}