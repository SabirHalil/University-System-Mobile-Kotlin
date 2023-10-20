package com.example.universitysystem.data.models

data class ChangePasswordBody(
    val studentId:Int,
    val oldPassword:String,
    val newPassword:String
)
