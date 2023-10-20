package com.example.universitysystem.network

import com.example.universitysystem.utils.StudentConstant.TOKEN
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RemoteDataSource {

    companion object{
        val BASE_URL = "http://192.168.2.2:8080/"
    }
    fun <Api> buildApi(
        api:Class<Api>,
        authToken:String? = TOKEN
    ):Api{
        val gson = GsonBuilder().create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                chain.proceed(chain.request().newBuilder().also {
                    it.addHeader("Authorization","Bearer $authToken")
                }.build())
            }.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(api)
    }
}