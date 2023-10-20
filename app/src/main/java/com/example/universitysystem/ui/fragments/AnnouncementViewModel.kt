package com.example.universitysystem.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.universitysystem.data.models.Announcement
import com.example.universitysystem.data.repository.AnnouncementRepository
import com.example.universitysystem.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class AnnouncementViewModel @Inject constructor(private val repository:AnnouncementRepository):ViewModel() {
    private val _announcements = MutableLiveData<UiState<ArrayList<Announcement>>>()
    val announcements : LiveData<UiState<ArrayList<Announcement>>>
        get() = _announcements

    fun getAnnouncements(){
        _announcements.value = UiState.Loading
        repository.getAllAnnouncements{
            _announcements.value = it
        }
    }
}