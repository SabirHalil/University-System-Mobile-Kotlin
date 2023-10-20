package com.example.universitysystem.data.repository

import com.example.universitysystem.data.models.Notification
import com.example.universitysystem.utils.UiState

interface NotificationRepository {
    fun getAllNotifications(studentId:Int,result:(UiState<ArrayList<Notification>>) -> Unit)
}