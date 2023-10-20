package com.example.universitysystem.data.repository

import com.example.universitysystem.data.models.ChangePasswordBody
import com.example.universitysystem.data.models.LoginBody
import com.example.universitysystem.data.models.TokenResponse
import com.example.universitysystem.network.AuthApi
import com.example.universitysystem.network.RemoteDataSource
import com.example.universitysystem.utils.UiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepositoryImp: AuthRepository {

    override fun login(body: LoginBody, result: (UiState<Pair<String, Int>>) -> Unit) {
        val authApi = RemoteDataSource().buildApi(AuthApi::class.java,null)

        authApi.getToken(body).enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.isSuccessful){
                    if (response.body() != null){
                        val token  = response.body()!!.token
                        val studentId  = response.body()!!.studentId
                        result.invoke(UiState.Success(Pair(token,studentId)))
                    }
                } else
                    result.invoke(UiState.Failure(response.message()))
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                result.invoke(UiState.Failure(t.localizedMessage))
            }

        })
    }

    override fun changePassword(
        body: ChangePasswordBody,
        result: (UiState<String>) -> Unit
    ) {
        val changePasswordApi = RemoteDataSource().buildApi(AuthApi::class.java)
        changePasswordApi.changePassword(body).enqueue(object :Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful){
                    result.invoke(UiState.Success("Password changed successfully"))
                }else{
                    result.invoke(UiState.Failure(response.message()))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                result.invoke(UiState.Failure(t.localizedMessage))
            }

        })
    }
}