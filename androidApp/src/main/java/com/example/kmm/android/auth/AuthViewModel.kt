package com.example.kmm.android.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmm.auth.LoginUseCase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _loginState = MutableStateFlow<String?>(null)
    val loginState: StateFlow<String?> = _loginState

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = "❌ Email or password cannot be empty."
            return
        }

        viewModelScope.launch {
            loginUseCase.execute(email, password).collect { result ->
                _loginState.value = if (result.isSuccess) {
                    "✅ You have logged in successfully!"
                } else {
                    mapFirebaseError(result.exceptionOrNull())
                }
            }
        }
    }

    private fun mapFirebaseError(error: Throwable?): String {
        if (error == null) return "❌ Login failed. Please try again."

        return when (error) {
            is FirebaseAuthInvalidCredentialsException -> "❌ Email or password is incorrect."
            else -> "❌ Login failed. Please try again."
        }
    }
}