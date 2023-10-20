package com.example.universitysystem.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.universitysystem.data.models.Notification
import com.example.universitysystem.data.repository.NotificationRepository
import com.example.universitysystem.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val repository:NotificationRepository):ViewModel() {
    private val _notifications = MutableLiveData<UiState<ArrayList<Notification>>>()
    val notifications :LiveData<UiState<ArrayList<Notification>>>
        get() = _notifications

    fun getNotifications(studentId:Int){
        _notifications.value = UiState.Loading
        repository.getAllNotifications(studentId = studentId) {
            _notifications.value = it
        }
    }
}