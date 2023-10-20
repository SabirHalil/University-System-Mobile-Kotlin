package com.example.universitysystem.data.models

data class StudentCertification(
    val id: Int,
    val birthDate: String,
    val birthPlace: String,
    val courseName: String,
    val dadName: String,
    val enrollmentDate: String,
    val facultyName: String,
    val firstName: String,
    val idCard: Long,
    val lastName: String,
    val momName: String,
    val studentNo: Int,
    val grade:Int
)