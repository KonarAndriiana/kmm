package com.example.kmm.android.auth

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Clock

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

    suspend fun saveUserToFirestore(uid: String, firstName: String, lastName: String, email: String) {
        val firestore = Firebase.firestore
        val timestamp = Clock.System.now().toEpochMilliseconds() / 1000.0

        val userData = mapOf(
            "id" to uid,
            "email" to email,
            "firstName" to firstName,
            "lastName" to lastName,
            "joined" to timestamp
        )

        firestore.collection("users").document(uid).set(userData)
    }
}