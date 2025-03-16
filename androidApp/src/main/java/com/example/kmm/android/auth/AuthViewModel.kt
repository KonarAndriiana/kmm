package com.example.kmm.android.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kmm.auth.LoginUseCase
import com.example.kmm.auth.RegisterUseCase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel
    (private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase) : ViewModel() {

    private val _loginState = MutableStateFlow<String?>(null)
    val loginState: StateFlow<String?> = _loginState

    private val _registerState = MutableStateFlow<String?>(null)
    val registerState: StateFlow<String?> = _registerState

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = "❌ Email or password cannot be empty."
            resetMessage()
            return
        }

        viewModelScope.launch {
            loginUseCase.execute(email, password).collect { result ->
                _loginState.value = if (result.isSuccess) {
                    "✅ You have logged in successfully!"
                } else {
                    mapFirebaseError(result.exceptionOrNull())
                }
                resetMessage()
            }
        }
    }

    fun register(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _registerState.value = "❌ Email or password cannot be empty."
            resetMessage()
            return
        }

        viewModelScope.launch {
            registerUseCase.execute(email, password).collect { result ->
                _registerState.value = if (result.isSuccess) {
                    "✅ Registration successful! You can now log in."
                } else {
                    mapFirebaseError(result.exceptionOrNull())
                }
                resetMessage()
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

    private fun resetMessage() {
        viewModelScope.launch {
            delay(2000)
            _loginState.value = null
            _registerState.value = null
        }
    }
}