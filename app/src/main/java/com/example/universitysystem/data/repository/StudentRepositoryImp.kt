package com.example.universitysystem.data.repository

import com.example.universitysystem.data.models.StudentCertification
import com.example.universitysystem.data.models.StudentInfo
import com.example.universitysystem.data.models.StudentUpdatableAttribute
import com.example.universitysystem.network.RemoteDataSource
import com.example.universitysystem.network.StudentApi
import com.example.universitysystem.utils.UiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentRepositoryImp:StudentRepository {
    override fun getStudentInfo(id: Int, result: (UiState<StudentInfo>) -> Unit) {
        val studentApi = RemoteDataSource().buildApi(StudentApi::class.java)
        studentApi.getStudentInfo(id).enqueue(object : Callback<StudentInfo> {
            override fun onResponse(call: Call<StudentInfo>, response: Response<StudentInfo>) {
                if (response.isSuccessful){
                    if (response.body() != null){
                       result.invoke(UiState.Success(response.body()!!))
                    }else
                      result.invoke(UiState.Failure(response.message()))
                }else
                    result.invoke(UiState.Failure(response.message()))
            }

            override fun onFailure(call: Call<StudentInfo>, t: Throwable) {
                result.invoke(UiState.Failure(t.localizedMessage))
            }
        })
    }

    override fun getStudentCertification(id: Int, result: (UiState<StudentCertification>) -> Unit) {
        val studentApi = RemoteDataSource().buildApi(StudentApi::class.java)
        studentApi.getStudentCertification(id).enqueue(object : Callback<StudentCertification> {
            override fun onResponse(call: Call<StudentCertification>, response: Response<StudentCertification>) {
                if (response.isSuccessful){
                    if (response.body() != null){
                        result.invoke(UiState.Success(response.body()!!))
                    }else
                        result.invoke(UiState.Failure(response.message()))
                }else
                    result.invoke(UiState.Failure(response.message()))
            }

            override fun onFailure(call: Call<StudentCertification>, t: Throwable) {
                result.invoke(UiState.Failure(t.localizedMessage))
            }
        })
    }

    override fun updateStudent(
        id: Int,
        student: StudentUpdatableAttribute,
        result: (UiState<String>) -> Unit
    ) {
        val studentApi = RemoteDataSource().buildApi(StudentApi::class.java)
        studentApi.updateStudent( id, student).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful){
                    result.invoke(UiState.Success("Student updated successfully!"))
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