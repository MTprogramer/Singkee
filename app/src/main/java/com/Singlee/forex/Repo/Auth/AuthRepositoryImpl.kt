package com.Singlee.forex.Repo.Auth

import android.content.Context
import android.util.Log
import com.Singlee.forex.DataModels.SettingData
import com.Singlee.forex.DataModels.UserData
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.Repo.safeApiCall
import com.Singlee.forex.Utils.SharedPrefs
import com.Singlee.forex.Utils.welcomeEmail
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor (
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val context: Context,
    private val sharedPrefs: SharedPrefs
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

               val userData = UserData(name , email, password , user.uid, "https://firebasestorage.googleapis.com/v0/b/singlee-18637.appspot.com/o/Profile.png?alt=media&token=b2d51bac-4d56-4e0c-b436-af5a3002b4b7",false  , false , false , false , "" , SettingData() )
               createUser(user.uid , userData)
               sharedPrefs.saveUser(userData)

               welcomeEmail(email)

               result
           }
            emit(response)
        }.catch {
            emit(Response.Error(it.message.toString()))
        }
    }

    override fun googleSignIn(credentials: AuthCredential): Flow<Response<AuthResult>> {
        return flow {
            val response = safeApiCall(context){
                Log.d("cred",credentials.toString())
                val result =  firebaseAuth.signInWithCredential(credentials).await()

                Log.d("result",result.toString())
                result.user?.email?.let { Log.d("email", it) }

                val userdata = UserData(result.user?.displayName!! , result.user?.email!! , "" , result.user!!.uid , result.user!!.photoUrl.toString() , false , true  , false , false , "" , SettingData())
                createUser(result.user!!.uid , userdata )
                result
            }
            emit(response)
        }.catch { emit(Response.Error("Something Wrong" + it.message)) }
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