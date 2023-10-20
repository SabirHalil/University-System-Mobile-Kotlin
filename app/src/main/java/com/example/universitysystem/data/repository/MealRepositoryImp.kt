package com.example.universitysystem.data.repository

import com.example.universitysystem.data.models.Meal
import com.example.universitysystem.network.MealApi
import com.example.universitysystem.network.RemoteDataSource
import com.example.universitysystem.utils.UiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealRepositoryImp:MealRepository {
    override fun getMeals(result: (UiState<ArrayList<Meal>>) -> Unit) {
        val mealApi = RemoteDataSource().buildApi(MealApi::class.java)
        mealApi.getMeals().enqueue(object : Callback<ArrayList<Meal>> {
            override fun onResponse(
                call: Call<ArrayList<Meal>>,
                response: Response<ArrayList<Meal>>
            ) {
                if (response.isSuccessful){
                    result.invoke(UiState.Success(data = response.body()!!))
                }else{
                    result.invoke(UiState.Failure(response.message()))
                }

            }

            override fun onFailure(call: Call<ArrayList<Meal>>, t: Throwable) {
                result.invoke(UiState.Failure(t.localizedMessage))
            }

        })
    }
}