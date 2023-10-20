package com.example.universitysystem.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.universitysystem.data.models.Meal
import com.example.universitysystem.data.repository.MealRepository
import com.example.universitysystem.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MealViewModel @Inject constructor(val repository: MealRepository) : ViewModel() {
    private val _meals = MutableLiveData<UiState<ArrayList<Meal>>>()
    val meals: LiveData<UiState<ArrayList<Meal>>>
        get() = _meals

    fun getMeals() {
        _meals.value = UiState.Loading
        repository.getMeals {
            _meals.value = it
        }
    }
}