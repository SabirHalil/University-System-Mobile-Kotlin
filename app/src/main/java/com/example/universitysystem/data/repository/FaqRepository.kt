package com.example.universitysystem.data.repository

import com.example.universitysystem.data.models.Faq
import com.example.universitysystem.utils.UiState

interface FaqRepository {
    fun getFaq(result:(UiState<ArrayList<Faq>>) -> Unit)
}