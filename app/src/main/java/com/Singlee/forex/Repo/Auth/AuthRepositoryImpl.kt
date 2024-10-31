package com.Singlee.forex.Repo.Auth

import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import com.Singlee.forex.DataModels.SettingData
import com.Singlee.forex.DataModels.UserData
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.Repo.safeApiCall
import com.Singlee.forex.Utils.OTP
import com.Singlee.forex.Utils.SharedPrefs
import com.Singlee.forex.Utils.welcomeEmail
import com.Singlee.forex.graph.AuthRouts
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

               val userData = UserData(name , email, password , user.uid, "",false  , false , false , false , "" , SettingData() )
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
            val response = safeApiCall(context) {
                Log.d("cred", credentials.toString())

                val result = firebaseAuth.signInWithCredential(credentials).await()
                val user = result.user
                Log.d("result", result.toString())
                user?.email?.let { Log.d("email", it) }

                if (user?.email != null) {
                    val userData = checkUser(user.email!!)

                    Log.d("result", "befpre password")
                    if (userData != null) {
                        Log.d("result", userData.password)
                        sharedPrefs.saveUser(userData)

                    } else {
                        Log.d("result", "register user")
                        // New user, create a new UserData instance and add to Firestore
                        val userdata = UserData(result.user?.displayName!! , result.user?.email!! , "" , result.user!!.uid , result.user!!.photoUrl.toString() , false , true  , false , false , "" , SettingData())
                        createUser(user.uid, userdata)
                        sharedPrefs.saveUser(userdata)
                        // Return the initial Google Sign-In result
                    }
                    result
                } else {
                    throw Exception("Email not found in Google Sign-In result")
                }
            }
            emit(response)
        }.catch { e ->
            Log.e("googleSignIn", "Error during Google Sign-In: ${e.message}")
            emit(Response.Error("Google Sign-In error: ${e.message}"))
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



    suspend fun checkUser(email: String): UserData? {
        val usersCollection = firestore.collection("users")
        return try {
            val emailQuery = usersCollection.whereEqualTo("email", email).get().await()
            if (!emailQuery.isEmpty) {
                Log.d("checkUser", "Email found in Firestore")
                val userDoc = emailQuery.documents.first()

                // Map Firestore document data to UserData
                userDoc.toObject(UserData::class.java)
            } else {
                Log.d("checkUser", "No user found with this email")
                null
            }
        } catch (e: Exception) {
            Log.e("checkUser", "Error checking user in Firestore: ${e.message}")
            null
        }
    }

}