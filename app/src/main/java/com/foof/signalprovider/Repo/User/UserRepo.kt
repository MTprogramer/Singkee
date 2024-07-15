package com.foof.signalprovider.Repo.User

import androidx.navigation.NavController
import com.foof.signalprovider.DataModels.UserData
import com.foof.signalprovider.Repo.Response
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.Flow

interface UserRepo
{
    suspend fun getUserData(userID : String) : Flow<Response<UserData>>
    suspend fun resetUserPass (user : AuthResult , password : String) : Flow<Response<String>>
    suspend fun userExists(email: String , navController: NavController): Flow<Response<Boolean>>
    suspend fun logout()
}