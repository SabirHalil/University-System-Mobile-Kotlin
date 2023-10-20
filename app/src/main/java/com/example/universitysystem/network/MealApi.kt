package com.example.universitysystem.network

import com.example.universitysystem.data.models.Meal
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {
    @GET("api/Meals")
    fun getMeals(): Call<ArrayList<Meal>>
}