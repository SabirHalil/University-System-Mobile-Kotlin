package com.example.universitysystem.data.models

data class StudentLectureDTO(
    val id:Int,
    val studentId:Int,
    var lectureName:String,
    var lectureCode:String,
    var semesterId:String,
    var takenYear:Int,
    var midterm:Int,
    var final:Int,
    var passed:Int
)
