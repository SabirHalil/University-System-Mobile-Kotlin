package com.example.universitysystem.data.repository

import com.example.universitysystem.data.models.Lecture
import com.example.universitysystem.data.models.StudentLecture
import com.example.universitysystem.data.models.StudentLectureDTO
import com.example.universitysystem.utils.UiState

interface LecturesRepository {
    fun getAllLectures(studentId: Int, result:(UiState<ArrayList<Lecture>>) -> Unit)
    fun getStudentLectures(id:Int, result:(UiState<ArrayList<StudentLectureDTO>>) -> Unit)
    fun addLecture(lecture: ArrayList<StudentLecture>, result: (UiState<String>) -> Unit)
    fun deleteLectures(studentId:Int, lectureId:Int,result:(UiState<String>) -> Unit)

}