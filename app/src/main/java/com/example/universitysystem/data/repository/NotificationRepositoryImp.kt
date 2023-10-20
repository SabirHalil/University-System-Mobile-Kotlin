package com.example.universitysystem.data.repository

import com.example.universitysystem.data.models.Notification
import com.example.universitysystem.network.NotificationApi
import com.example.universitysystem.network.RemoteDataSource
import com.example.universitysystem.utils.UiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationRepositoryImp:NotificationRepository {

    override fun getAllNotifications(id:Int, result: (UiState<ArrayList<Notification>>) -> Unit) {
        val notificationApi = RemoteDataSource().buildApi(NotificationApi::class.java)
        notificationApi.getAllNotifications(id).enqueue(object : Callback<ArrayList<Notification>> {
            override fun onResponse(
                call: Call<ArrayList<Notification>>,
                response: Response<ArrayList<Notification>>
            ) {
                if (response.isSuccessful){
                    if (response.body() != null){
                        result.invoke(UiState.Success(response.body()!!))
                    }else
                        result.invoke(UiState.Failure(response.message()))
                }else
                    result.invoke(UiState.Failure(response.message()))

            }

            override fun onFailure(call: Call<ArrayList<Notification>>, t: Throwable) {
                result.invoke(UiState.Failure(t.localizedMessage))
            }

        })
    }
}