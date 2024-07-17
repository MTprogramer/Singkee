package com.Singlee.forex.Repo.Password

import com.Singlee.forex.Repo.Response
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PasswordRepoImpl @Inject constructor(
  private val firebaseAuth: FirebaseAuth
) : PasswordRepo {

    override suspend fun resetPassword(email: String): Flow<Response<Void?>> = flow {
        try {
            emit(Response.Loading)
            firebaseAuth.sendPasswordResetEmail(email).await()
            emit(Response.Success(null))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Oops, something went wrong."))
        }
    }

    override suspend fun changePassword(currentPassword: String, newPassword: String): Flow<Response<Void?>> = flow {
        try {
            emit(Response.Loading)
            val user = firebaseAuth.currentUser
            if (user != null) {
                val credential = EmailAuthProvider.getCredential(user.email!!, currentPassword)
                user.reauthenticate(credential).await()
                user.updatePassword(newPassword).await()
                emit(Response.Success(null))
            } else {
                emit(Response.Error("User is not logged in"))
            }
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Oops, something went wrong."))
        }
    }
}