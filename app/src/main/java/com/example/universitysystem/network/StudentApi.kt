package com.example.universitysystem.network

import com.example.universitysystem.data.models.StudentCertification
import com.example.universitysystem.data.models.StudentInfo
import com.example.universitysystem.data.models.StudentUpdatableAttribute
import retrofit2.Call
import retrofit2.http.*

interface StudentApi {
    @GET("api/students")
    fun getStudentInfo(@Query("studentId") id:Int): Call<StudentInfo>
    @GET("api/students/{id}")
    fun getStudentCertification(@Path("id") id:Int): Call<StudentCertification>
    @PUT("api/Students/{id}")
    fun updateStudent(@Path("id") id: Int, @Body student: StudentUpdatableAttribute): Call<Void>
}