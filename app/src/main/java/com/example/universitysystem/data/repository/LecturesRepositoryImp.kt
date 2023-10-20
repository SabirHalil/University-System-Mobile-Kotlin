package com.example.universitysystem.data.repository

import com.example.universitysystem.data.models.Lecture
import com.example.universitysystem.data.models.StudentLecture
import com.example.universitysystem.data.models.StudentLectureDTO
import com.example.universitysystem.network.LecturesApi
import com.example.universitysystem.network.RemoteDataSource
import com.example.universitysystem.utils.StudentConstant.TOKEN
import com.example.universitysystem.utils.UiState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LecturesRepositoryImp:LecturesRepository {

    override fun getAllLectures(studentId: Int, result: (UiState<ArrayList<Lecture>>) -> Unit) {
        val lecturesApi = RemoteDataSource().buildApi(LecturesApi::class.java,TOKEN)
        lecturesApi.getAllLectures(studentId = studentId).enqueue(object : Callback<ArrayList<Lecture>> {
            override fun onResponse(
                call: Call<ArrayList<Lecture>>,
                response: Response<ArrayList<Lecture>>
            ) {
                if (response.isSuccessful){
                    if (response.body()?.isNotEmpty() == true){
                        result.invoke(UiState.Success(response.body()!!))
                    }else
                        result.invoke(UiState.Failure(response.message()))
                }else
                    result.invoke(UiState.Failure(response.message()))
            }

            override fun onFailure(call: Call<ArrayList<Lecture>>, t: Throwable) {
                result.invoke(UiState.Failure(t.localizedMessage))
            }
        })
    }


    override fun getStudentLectures(
        id: Int,
        result: (UiState<ArrayList<StudentLectureDTO>>) -> Unit
    ) {
        val lectureApi = RemoteDataSource().buildApi(LecturesApi::class.java)
        lectureApi.getStudentLectures( studentId = id).enqueue(object : Callback<ArrayList<StudentLectureDTO>> {
            override fun onResponse(
                call: Call<ArrayList<StudentLectureDTO>>,
                response: Response<ArrayList<StudentLectureDTO>>
            ) {
                if (response.isSuccessful){
                    if (response.body()?.isNotEmpty() == true){
                        result.invoke(UiState.Success(response.body()!!))
                    }else
                        result.invoke(UiState.Failure(response.message()))
                }else
                    result.invoke(UiState.Failure(response.message()))

            }

            override fun onFailure(call: Call<ArrayList<StudentLectureDTO>>, t: Throwable) {
                result.invoke(UiState.Failure(t.localizedMessage))
            }

        })
    }

    override fun addLecture(

        lecture: ArrayList<StudentLecture>,
        result: (UiState<String>) -> Unit
    ) {
        val lectureApi = RemoteDataSource().buildApi(LecturesApi::class.java)
        lectureApi.addLectures(lecture = lecture).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful){
                    result.invoke(UiState.Success("Lecture added successfully!"))
                }else{
                    result.invoke(UiState.Failure(response.message()))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                result.invoke(UiState.Failure(t.localizedMessage))
            }

        })
    }

    override fun deleteLectures(
        studentId:Int,
        lectureId: Int,
        result: (UiState<String>) -> Unit
    ) {
        val lecturesApi = RemoteDataSource().buildApi(LecturesApi::class.java)
        lecturesApi.deleteLectures(studentId = studentId, lectureId = lectureId).enqueue(
            object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful){
                        result.invoke(UiState.Success("Lecture deleted successfully"))
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