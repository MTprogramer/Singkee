package com.foof.signalprovider.Repo.User

import com.foof.signalprovider.DataModels.UserData
import com.foof.signalprovider.Repo.Response
import kotlinx.coroutines.flow.Flow

interface UserRepo
{
    suspend fun userUid(): String
    suspend fun isLoggedIn(): Boolean
    suspend fun getUserData(userID : String) : Flow<Response<UserData>>
    suspend fun userExists(email: String): Flow<Response<Boolean>>
    suspend fun logout()
}