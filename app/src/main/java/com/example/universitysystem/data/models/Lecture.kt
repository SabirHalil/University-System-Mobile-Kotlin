package com.example.universitysystem.data.models

data class Lecture(
    val id:Int,
    val courseId:Int,
    val lectureName:String,
    val lectureCode:String,
    val lectureCredit:Int
)
