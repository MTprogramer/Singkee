package com.foof.signalprovider.Repo.User

import android.content.Context
import android.util.Log
import com.foof.signalprovider.DataModels.UserData
import com.foof.signalprovider.Repo.Response
import com.foof.signalprovider.Repo.safeApiCall
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepoImpl @Inject constructor (
   private val firebaseAuth: FirebaseAuth,
   private val firebaseFirestore: FirebaseFirestore,
    private val context: Context
) : UserRepo {

    override suspend fun userUid(): String = firebaseAuth.currentUser?.uid ?: ""
    override suspend fun isLoggedIn(): Boolean = firebaseAuth.currentUser == null



    override suspend fun userExists(email: String): Flow<Response<Boolean>> {
        return flow {
            val response = safeApiCall(context) {
                val usersCollection = firebaseFirestore.collection("users")
                 val emailC = usersCollection.whereEqualTo("email", email).get().await()
                val exist = !emailC.isEmpty
                exist
            }
            emit(response)
        }
    }
    override suspend fun getUserData(userID : String): Flow<Response<UserData>> {
        return flow {
            val documentSnapshot = safeApiCall(context){
                val response = firebaseFirestore.collection("users").document(userID).get().await()
                val user = response.toObject(UserData::class.java)!!
                user
            }
            emit(documentSnapshot)
        }
    }




    override suspend fun logout() = firebaseAuth.signOut()

}