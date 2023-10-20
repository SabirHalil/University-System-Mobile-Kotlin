package com.example.universitysystem.network

import com.example.universitysystem.data.models.Announcement
import retrofit2.Call
import retrofit2.http.GET

interface AnnouncementApi {
    @GET("api/Announcements")
    fun getAllAnnouncements(): Call<ArrayList<Announcement>>
}