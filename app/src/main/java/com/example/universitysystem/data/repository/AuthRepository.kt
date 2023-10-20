package com.example.universitysystem.data.repository

import com.example.universitysystem.data.models.ChangePasswordBody
import com.example.universitysystem.data.models.LoginBody
import com.example.universitysystem.utils.UiState

interface AuthRepository {
    fun login(body: LoginBody,result: (UiState<Pair<String, Int>>) -> Unit)
    fun changePassword(body: ChangePasswordBody,result: (UiState<String>) -> Unit)
}