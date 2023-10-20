package com.example.universitysystem.data.models

data class Announcement(
    val id:Int,
    val title:String,
    val description:String,
    val type:Int,
    val date:String,
    var expanded :Boolean = false
)
