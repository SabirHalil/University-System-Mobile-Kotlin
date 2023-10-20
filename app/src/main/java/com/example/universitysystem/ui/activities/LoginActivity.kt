package com.example.universitysystem.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.universitysystem.data.models.LoginBody
import com.example.universitysystem.databinding.ActivityLoginBinding
import com.example.universitysystem.ui.fragments.AnnouncementViewModel
import com.example.universitysystem.utils.*
import com.example.universitysystem.utils.StudentConstant.ID
import com.example.universitysystem.utils.StudentConstant.TOKEN
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private val viewModel :AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observer()
        binding.btnLogin.setOnClickListener {
            if (validation()){
                viewModel.login( LoginBody(cardId = binding.etNoLogin.text.toString().toLong(),password = binding.etPasswordLogin.text.toString()))
            }
        }
    }

    private fun validation(): Boolean {
        var isValid = true

        if (binding.etNoLogin.text.isNullOrEmpty()){
            isValid = false
            toast("Enter Student No")
        }
        if (binding.etPasswordLogin.text.isNullOrEmpty()){
            isValid = false
            toast("Enter password")
        }
        return isValid
    }

    private fun observer(){
        viewModel.login.observe(this) { state ->
            when(state){
                is UiState.Loading -> {
                    binding.btnLogin.text = ""
                    binding.loginProgress.show()
                }
                is UiState.Failure -> {
                    binding.btnLogin.text = "Login"
                    binding.loginProgress.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.btnLogin.text = "Login"
                    binding.loginProgress.hide()
                    TOKEN = state.data.first
                    ID = state.data.second
                    println(state.data.second)
                    toast("Login done successfully!")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}