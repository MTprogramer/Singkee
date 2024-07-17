package com.Singlee.forex.Repo.User

import android.content.Context
import androidx.navigation.NavController
import com.Singlee.forex.DataModels.UserData
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.Repo.safeApiCall
import com.Singlee.forex.Utils.OTP
import com.Singlee.forex.graph.AuthRouts
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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


    override suspend fun userExists(email: String , navController: NavController): Flow<Response<Boolean>> {
        return flow {
            var password = ""
            val response = safeApiCall(context) {
                val response = safeApiCall(context) {
                    val usersCollection = firebaseFirestore.collection("users")
                    val emailC = usersCollection.whereEqualTo("email", email).get().await()
                    if (!emailC.isEmpty)
                    {
                        val userdoc = emailC.documents.first()
                        password = userdoc.getString("password").toString()
                    }
                    !emailC.isEmpty
                }

                if (response is Response.Success) {
                    if (response.data && password.isNotEmpty()) {
                        OTP.otpEmail(email)
                        navController.navigate("${AuthRouts.OTPRoute.route}/$email/$password")
                    } else {
                        emit(Response.Error("User does not exist"))
                        return@safeApiCall
                    }
                }
                emit(response)
            }
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

    override suspend fun resetUserPass(user : AuthResult  , password : String): Flow<Response<String>> {
        return flow {
            val response = safeApiCall(context){
                user.user?.let {
                    user.user!!.updatePassword(password).await()
                    val userRef = user.user?.let { firebaseFirestore.collection("users").document(it.uid) }
                    userRef?.update("password",password)?.await()
                }
                firebaseAuth.signOut()
                "Password Updated Successfully"
            }
            emit(response)

        }.catch {
            emit(Response.Error("Password not changed"))
        }
    }


    override suspend fun logout() = firebaseAuth.signOut()

}