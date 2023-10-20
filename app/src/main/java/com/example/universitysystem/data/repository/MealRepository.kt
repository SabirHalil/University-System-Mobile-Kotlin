package com.example.universitysystem.data.repository

import com.example.universitysystem.data.models.Announcement
import com.example.universitysystem.data.models.Meal
import com.example.universitysystem.utils.UiState

interface MealRepository {
    fun getMeals(result:(UiState<ArrayList<Meal>>) -> Unit)
}