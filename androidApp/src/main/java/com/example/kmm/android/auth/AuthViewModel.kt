package com.example.kmm.android.auth

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel
    (private val loginUseCase: LoginUseCase,
     private val registerUseCase: RegisterUseCase,
     private val authRepository: AuthRepository) : ViewModel() {

    private val _loginState = MutableStateFlow<String?>(null)
    val loginState: StateFlow<String?> = _loginState

    private val _registerState = MutableStateFlow<String?>(null)
    val registerState: StateFlow<String?> = _registerState

    var showRegistrationSuccess = mutableStateOf(false)
        private set

    fun markRegistrationSuccessShown() {
        showRegistrationSuccess.value = false
    }

    fun login(email: String, password: String) {
        val validationMessage = validateLoginInputs(email, password)
        if (validationMessage != null) {
            _loginState.value = validationMessage
            resetMessage()
            return
        }

        viewModelScope.launch {
            loginUseCase.execute(email, password).collect { result ->
                _loginState.value = if (result.isSuccess) {
                    "✅ You have logged in successfully!"
                } else {
                    mapFirebaseError(result.exceptionOrNull(), isLogin = true)
                }
                resetMessage()
            }
        }
    }

    fun register(email: String, password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            _registerState.value = "❌ Passwords do not match"
            resetMessage()
            return
        }
        val validationMessage = validateRegisterInputs(email, password)
        if (validationMessage != null) {
            _registerState.value = validationMessage
            resetMessage()
            return
        }

        viewModelScope.launch {
            registerUseCase.execute(email, password).collect { result ->
                _registerState.value = if (result.isSuccess) {
                    showRegistrationSuccess.value = true
                    "✅ Registration successful! You can now log in"
                } else {
                    mapFirebaseError(result.exceptionOrNull(), isLogin = false)
                }
                resetMessage()
            }
        }
    }

    fun getRegistrationSuccessMessage() = "Registration successful! You can now log in"

    fun resetPassword(
        email: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val validationMessage = validateResetPassword(email)
        if (validationMessage != null) {
            onError(validationMessage)
            return
        }

        viewModelScope.launch {
            authRepository.resetPassword(email).collect { result ->
                if (result.isSuccess) {
                    onSuccess()
                } else {
                    onError(result.exceptionOrNull()?.message ?: "Something went wrong")
                }
            }
        }
    }

    private fun validateResetPassword(email: String): String? {
        if (email.isBlank()) return "Enter your email to continue"
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "Please enter a valid email address"
        }
        return null
    }

    private fun validateLoginInputs(email: String, password: String): String? {
        if (email.isBlank() && password.isBlank()) return "❌ Both fields must be completed"
        if (email.isBlank()) return "❌ Email cannot be empty"
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "❌ Please enter a valid email address"
        }
        if (password.isBlank()) return "❌ Password cannot be empty"
        return null
    }

    private fun validateRegisterInputs(email: String, password: String): String? {
        if (email.isBlank() && password.isBlank()) return "❌ Both fields must be completed"
        if (email.isBlank()) return "❌ Email cannot be empty"
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return "❌ Please enter a valid email address"
        }
        if (password.isBlank()) return "❌ Password cannot be empty"
        if (password.length < 6) return "❌ Password must be at least 6 characters long"
        if (!password.any { it.isUpperCase() }) return "❌ Password must contain at least one uppercase letter"
        if (!password.any { it.isDigit() }) return "❌ Password must contain at least one number"
        return null
    }

    private fun mapFirebaseError(error: Throwable?, isLogin: Boolean): String {
        if (error == null) {
            return if (isLogin) {
                "❌ Login failed. Please try again"
            } else {
                "❌ Registration failed. Please try again later"
            }
        }

        return when (error) {
            is FirebaseAuthInvalidCredentialsException -> {
                if (isLogin) "❌ Email or password is incorrect"
                else "❌ Please enter a valid email address"
            }
            is FirebaseAuthUserCollisionException -> "❌ This email is already registered"
            is FirebaseNetworkException -> "❌ Network error. Please check your internet connection"
            else -> {
                if (isLogin) "❌ Login failed. Please try again later"
                else "❌ Registration failed. Please try again later"
            }
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