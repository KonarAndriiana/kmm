package com.example.kmm.android.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AuthViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {

            val authRepository = AuthRepository()
            val loginUseCase = LoginUseCase(authRepository)
            val registerUseCase = RegisterUseCase(authRepository)

            return AuthViewModel(loginUseCase, registerUseCase, authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}