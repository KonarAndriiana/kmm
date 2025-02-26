package com.example.kmm.auth

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
}