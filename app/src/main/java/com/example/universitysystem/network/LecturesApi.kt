package com.example.universitysystem.network

import com.example.universitysystem.data.models.Lecture
import com.example.universitysystem.data.models.StudentLecture
import com.example.universitysystem.data.models.StudentLectureDTO
import retrofit2.Call
import retrofit2.http.*

interface LecturesApi {
    @GET("api/Lectures/{studentId}")
    fun getAllLectures(@Path("studentId") studentId: Int):Call<ArrayList<Lecture>>

    @GET("api/StudentLectures/{studentId}")
    fun getStudentLectures(@Path("studentId") studentId: Int):Call<ArrayList<StudentLectureDTO>>

    @DELETE("api/StudentLectures/{studentId}/{lectureId}")
    fun deleteLectures(
        @Path("studentId") studentId: Int,
        @Path("lectureId") lectureId: Int
    ): Call<Void>

    @POST("api/lectures")
    fun addLectures(@Body lecture: ArrayList<StudentLecture>):Call<Void>
}