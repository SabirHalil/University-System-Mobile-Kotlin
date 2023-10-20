package com.example.universitysystem.ui.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.universitysystem.data.models.ChangePasswordBody
import com.example.universitysystem.data.models.LoginBody
import com.example.universitysystem.data.repository.AuthRepository
import com.example.universitysystem.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(val repository: AuthRepository):ViewModel() {
    private val _login = MutableLiveData<UiState<Pair<String,Int>>>()
    val login: LiveData<UiState<Pair<String,Int>>>
        get() = _login

    private val _changePassword = MutableLiveData<UiState<String>>()
    val changePassword: LiveData<UiState<String>>
        get() = _changePassword

    fun login(body: LoginBody){
        _login.value =UiState.Loading
        repository.login(body){
        _login.value = it }
    }

    fun changePassword(body: ChangePasswordBody){
        _changePassword.value =UiState.Loading
        repository.changePassword(body){
            _changePassword.value = it }
    }

}