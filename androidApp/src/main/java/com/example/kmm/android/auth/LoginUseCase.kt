package com.example.kmm.android.auth

import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val authRepository: AuthRepository) {
    fun execute(email: String, password: String): Flow<Result<FirebaseUser?>> {
        return authRepository.login(email, password)
    }
}