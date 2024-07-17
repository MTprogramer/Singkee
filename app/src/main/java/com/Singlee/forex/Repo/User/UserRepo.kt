package com.Singlee.forex.Repo.User

import androidx.navigation.NavController
import com.Singlee.forex.DataModels.UserData
import com.Singlee.forex.Repo.Response
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface UserRepo
{
    suspend fun getUserData(userID : String) : Flow<Response<UserData>>
    suspend fun resetUserPass (user : AuthResult , password : String) : Flow<Response<String>>
    suspend fun userExists(email: String , navController: NavController): Flow<Response<Boolean>>
    suspend fun logout()
}