package com.Singlee.forex.Repo.Auth

import com.Singlee.forex.Repo.Response
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository
{
    fun loginUser(email: String , password : String) : Flow<Response<AuthResult>>
    fun registerUser(email: String , password : String , name : String) : Flow<Response<AuthResult>>
    fun googleSignIn(credentials : AuthCredential ) : Flow<Response<AuthResult>>
}