package com.foof.signalprovider.Repo.Auth

import android.content.Context
import android.util.Log
import com.foof.signalprovider.DataModels.UserData
import com.foof.signalprovider.Repo.Response
import com.foof.signalprovider.Repo.User.UserRepo
import com.foof.signalprovider.Repo.safeApiCall
import com.foof.signalprovider.Utils.welcomeEmail
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserInfo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor (
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val context: Context
): AuthRepository {

    override fun loginUser(email: String, password: String): Flow<Response<AuthResult>> {
        return flow {
            val result = safeApiCall(context){
               val response = firebaseAuth.signInWithEmailAndPassword(email,password).await()
                response
            }
            emit(result)
        }.catch {
            emit(Response.Error("Wrong Information"))
        }
    }

    override fun registerUser(email: String, password: String , name : String): Flow<Response<AuthResult>> {
        return flow {
           val response = safeApiCall(context){
               val result = firebaseAuth.createUserWithEmailAndPassword(email,password).await()
               val user  = result.user!!


               val userData = UserData(name , email, password , user.uid, "https://firebasestorage.googleapis.com/v0/b/singlee-18637.appspot.com/o/Profile.png?alt=media&token=b2d51bac-4d56-4e0c-b436-af5a3002b4b7",false )
               createUser(user.uid , userData)

               welcomeEmail(email)

               result
           }
            emit(response)
        }.catch {
            emit(Response.Error(it.message.toString()))
        }
    }
    suspend fun createUser(uid: String, user: UserData) :Boolean{
        val userRef = firestore.collection("users").document(uid)
        try {
            userRef.set(user).await()
            return true
        } catch (e: Exception) {
            return false
        }
    }



}