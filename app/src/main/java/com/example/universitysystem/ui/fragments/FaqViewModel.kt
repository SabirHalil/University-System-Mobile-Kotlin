package com.example.universitysystem.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.universitysystem.data.models.Announcement
import com.example.universitysystem.data.models.Faq
import com.example.universitysystem.data.repository.FaqRepository
import com.example.universitysystem.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FaqViewModel @Inject constructor(private val repository: FaqRepository) : ViewModel() {
    private val _faq = MutableLiveData<UiState<ArrayList<Faq>>>()
    val faq: LiveData<UiState<ArrayList<Faq>>>
        get() = _faq

    fun getAnnouncements() {
        _faq.value = UiState.Loading
        repository.getFaq {
            _faq.value = it
        }
    }
}