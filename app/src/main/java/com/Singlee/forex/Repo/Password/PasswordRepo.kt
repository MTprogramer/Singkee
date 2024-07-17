package com.Singlee.forex.Repo.Password

import com.Singlee.forex.Repo.Response
import kotlinx.coroutines.flow.Flow

interface PasswordRepo
{
    suspend fun resetPassword(email: String): Flow<Response<Void?>>
    suspend fun changePassword(currentPassword: String, newPassword: String): Flow<Response<Void?>>
}