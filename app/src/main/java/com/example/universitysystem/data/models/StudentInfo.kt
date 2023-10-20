package com.example.universitysystem.data.models

data class StudentInfo(
    val id:Int,
    val firstName:String,
    val lastName:String,
    val studentNo:Int,
    val gpa:Double,
    val grade:Int,
    var address:String,
    var phone:String,
    var iban:String
)