package com.example.universitysystem.data.models

data class StudentLecture(
    val studentId:Int,
    var lectureId:Int,
    var semesterId:Int,
    var takenYear:Int,
    var midterm:Int=0,
    var final:Int = 0,
    var passed:Int =0
)
