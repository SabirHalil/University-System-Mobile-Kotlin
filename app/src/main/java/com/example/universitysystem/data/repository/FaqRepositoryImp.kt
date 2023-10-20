package com.example.universitysystem.data.repository

import com.example.universitysystem.data.models.Faq
import com.example.universitysystem.network.FaqApi
import com.example.universitysystem.network.RemoteDataSource
import com.example.universitysystem.utils.UiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FaqRepositoryImp:FaqRepository {
    override fun getFaq(result: (UiState<ArrayList<Faq>>) -> Unit) {
        val faqApi = RemoteDataSource().buildApi(FaqApi::class.java)
        faqApi.getFaq().enqueue(object : Callback<ArrayList<Faq>> {
            override fun onResponse(
                call: Call<ArrayList<Faq>>,
                response: Response<ArrayList<Faq>>) {

                if (response.isSuccessful && response.body()?.isNotEmpty() == true){
                    result.invoke(UiState.Success(response.body()!!))
                }else{
                    result.invoke(UiState.Failure(response.message()))
                }

            }

            override fun onFailure(call: Call<ArrayList<Faq>>, t: Throwable) {
                result.invoke(UiState.Failure(t.localizedMessage))
            }

        })
    }
}