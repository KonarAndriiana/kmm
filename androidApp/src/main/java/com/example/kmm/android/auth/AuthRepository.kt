package com.example.kmm.android.auth

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AuthRepository {
    private val auth = Firebase.auth

    fun login(email: String, password: String): Flow<Result<FirebaseUser?>> = flow {
        val user = auth.signInWithEmailAndPassword(email, password).user
        emit(Result.success(user))
    }.catch { emit(Result.failure(it)) }

    fun register(email: String, password: String): Flow<Result<FirebaseUser?>> = flow {
        val user = auth.createUserWithEmailAndPassword(email, password).user
        emit(Result.success(user))
    }.catch { emit(Result.failure(it)) }

    fun resetPassword(email: String): Flow<Result<Unit>> = flow {
        try {
            Firebase.auth.sendPasswordResetEmail(email)
            emit(Result.success(Unit))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}