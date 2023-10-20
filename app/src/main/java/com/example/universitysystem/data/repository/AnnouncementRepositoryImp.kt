package com.example.universitysystem.data.repository

import com.example.universitysystem.data.models.Announcement
import com.example.universitysystem.network.AnnouncementApi
import com.example.universitysystem.network.RemoteDataSource
import com.example.universitysystem.utils.UiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnnouncementRepositoryImp:AnnouncementRepository {
    override fun getAllAnnouncements(
        result: (UiState<ArrayList<Announcement>>) -> Unit
    ) {
        val announcementApi = RemoteDataSource().buildApi(AnnouncementApi::class.java)
        announcementApi.getAllAnnouncements().enqueue(object :
            Callback<ArrayList<Announcement>> {
            override fun onResponse(
                call: Call<ArrayList<Announcement>>,
                response: Response<ArrayList<Announcement>>
            ) {
             if (response.isSuccessful){
                 if (response.body() != null){
                     result.invoke(UiState.Success(response.body()!!))
                 }else{
                     result.invoke(UiState.Failure(response.message()))
                 }
             }else{
                 result.invoke(UiState.Failure(response.message()))
             }
            }
            override fun onFailure(call: Call<ArrayList<Announcement>>, t: Throwable) {
                result.invoke(UiState.Failure(t.localizedMessage))
            }
        })
    }
}