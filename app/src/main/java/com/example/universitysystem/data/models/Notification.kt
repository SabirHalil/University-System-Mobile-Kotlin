package com.example.universitysystem.data.models

data class Notification(
    val id:Int,
    val studentId:Int,
    val title:String,
    val description:String,
    val date:String,
    var expanded:Boolean = false
)
