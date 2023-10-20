package com.example.universitysystem.data.repository

import com.example.universitysystem.data.models.StudentCertification
import com.example.universitysystem.data.models.StudentInfo
import com.example.universitysystem.data.models.StudentUpdatableAttribute
import com.example.universitysystem.utils.UiState

interface  StudentRepository {
    fun getStudentInfo(id:Int, result: (UiState<StudentInfo>) -> Unit)
    fun getStudentCertification(id:Int, result: (UiState<StudentCertification>) -> Unit)
    fun updateStudent(id:Int, student: StudentUpdatableAttribute, result: (UiState<String>)->Unit)
}