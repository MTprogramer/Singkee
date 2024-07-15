package com.foof.signalprovider.Repo.Password

import com.foof.signalprovider.Repo.Response
import kotlinx.coroutines.flow.Flow

interface PasswordRepo
{
    suspend fun resetPassword(email: String): Flow<Response<Void?>>
    suspend fun changePassword(currentPassword: String, newPassword: String): Flow<Response<Void?>>
}