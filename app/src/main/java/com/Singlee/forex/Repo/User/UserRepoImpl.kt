package com.Singlee.forex.Repo.User

import android.content.Context
import android.util.Log
import androidx.compose.ui.unit.Constraints
import androidx.navigation.NavController
import com.Singlee.forex.DataModels.SettingData
import com.Singlee.forex.DataModels.UserData
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.Repo.safeApiCall
import com.Singlee.forex.Utils.Constant
import com.Singlee.forex.Utils.OTP
import com.Singlee.forex.Utils.SharedPrefs
import com.Singlee.forex.graph.AuthRouts
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
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
    private val context: Context,
    private val sharedPrefs: SharedPrefs
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

//    fun changeUserEmail(newEmail: String) : Boolean {
//        val user = FirebaseAuth.getInstance().currentUser
//        return user?.updateEmail(newEmail)
//            ?.addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("email",  "Email updated successfully.")
//                } else {
//                    Log.d("email", task.exception?.message.toString())
//                }
//            }?.isSuccessful?:false
//    }
    fun changeUserPassword(newPassword: String):Boolean {
        val user = FirebaseAuth.getInstance().currentUser
       return user?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Password", "Password updated successfully.")
                } else {
                    Log.d("Password", task.exception?.message.toString())
                }
            }?.isSuccessful?:false
    }

    override suspend fun updateUserData(userData: UserData): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        try {

            if (!userData.is_third_party)
            {
                var reAuthSuccess = false
                // Re-authenticate user
                reAuthenticateUser(sharedPrefs.getString(Constant.EMAIL) ?: "", sharedPrefs.getString(Constant.PASSWORD) ?: "") {
                        success, message, id ->
                    reAuthSuccess = success
                    Log.d("reAuth", "Success: $success, Message: $message, ID: $id")


                }
                if (!reAuthSuccess) {
                    emit(Response.Error("Re-authentication failed"))
                    return@flow
                }

//            // Update email
//            val emailSuccess = changeUserEmail(newEmail)
//            if (!emailSuccess) {
//                emit(Response.Error("Email update failed"))
//                return@flow
//            }

                // Update password
                val passwordSuccess = changeUserPassword(userData.password)
                if (!passwordSuccess) {
                    emit(Response.Error("Password update failed"))
                    return@flow
                }
            }

            val updatedData: MutableMap<String, Any> = mutableMapOf(
                "name" to userData.name
            )
            // Conditionally add the "password" field
            if (!userData.is_third_party) {
                updatedData["password"] = userData.password
            }

            // Update Firestore document
            val firestoreUpdate = firebaseFirestore.collection("users").document(userData.vid).update(updatedData)
            firestoreUpdate.await() // Use await() to wait for completion

            sharedPrefs.save(Constant.NAME , userData.name)
            if (!userData.is_third_party)
                sharedPrefs.save(Constant.PASSWORD , userData.password)

            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "An unknown error occurred"))
        }
    }

    override suspend fun updateSettingData(SettingData: SettingData): Flow<Response<Boolean>> {
        return flow<Response<Boolean>> {
            val response = safeApiCall(context){
                val userRef = firebaseFirestore.collection("users").document(sharedPrefs.getString(Constant.USER_ID) ?: "")
                userRef.update(SettingData.toMap()).await()
                sharedPrefs.saveSetting(SettingData)
                true
            }
            emit(response)
        }.catch {
            emit(Response.Error(it.message ?: "An unknown error occurred"))
        }
    }

    fun reAuthenticateUser(email: String, password: String, onComplete: (Boolean, String? , String?) -> Unit) {
        val user = firebaseAuth.currentUser
        val credential = EmailAuthProvider.getCredential(email, password)

        user?.reauthenticate(credential)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, "Re-authentication successful." , user.uid)
                } else {
                    onComplete(false, task.exception?.message , null)
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


    override suspend fun logout()
    {
        firebaseAuth.signOut()
        sharedPrefs.clearPreference()
    }

}