package com.example.universitysystem.network

import com.example.universitysystem.data.models.Faq
import retrofit2.Call
import retrofit2.http.GET

interface FaqApi {
    @GET("api/Faq")
    fun getFaq():Call<ArrayList<Faq>>
}