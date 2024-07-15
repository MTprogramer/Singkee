package com.foof.signalprovider.Repo.Auth

import com.foof.signalprovider.Repo.Response
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository
{
    fun loginUser(email: String , password : String) : Flow<Response<AuthResult>>
    fun registerUser(email: String , password : String , name : String) : Flow<Response<AuthResult>>
}