package com.Singlee.forex.Repo.User

import androidx.navigation.NavController
import com.Singlee.forex.DataModels.SettingData
import com.Singlee.forex.DataModels.UserData
import com.Singlee.forex.Repo.Response
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface UserRepo
{
    suspend fun getUserData(userID : String) : Flow<Response<UserData>>
    suspend fun resetUserPass (user : AuthResult , password : String) : Flow<Response<String>>
    suspend fun userExists(email: String , navController: NavController): Flow<Response<Boolean>>
    suspend fun updateUserData(userData: UserData) : Flow<Response<Boolean>>
    suspend fun updateSettingData(userData: UserData) : Flow<Response<Boolean>>
    suspend fun updateAvtar(imageUri : String) : Flow<Response<Boolean>>
    suspend fun getRandomThreeUsersImageUrls() : Flow<Response<List<String>>>
    suspend fun logout()
}