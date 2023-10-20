package com.example.universitysystem.data.repository

import com.example.universitysystem.data.models.Announcement
import com.example.universitysystem.data.models.Lecture
import com.example.universitysystem.utils.UiState

interface AnnouncementRepository {
    fun getAllAnnouncements(result:(UiState<ArrayList<Announcement>>) -> Unit)
}