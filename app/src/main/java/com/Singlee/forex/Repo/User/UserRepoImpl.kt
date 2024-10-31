package com.Singlee.forex.Repo.User

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import com.Singlee.forex.DataModels.UserData
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.Repo.safeApiCall
import com.Singlee.forex.Utils.Constant
import com.Singlee.forex.Utils.OTP
import com.Singlee.forex.Utils.SharedPrefs
import com.Singlee.forex.graph.AuthRouts
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepoImpl @Inject constructor (
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore,
    private val context: Context,
    private val sharedPrefs: SharedPrefs,
    private val firebaseStorage: FirebaseStorage
) : UserRepo {



    private val _passwordUpdateSuccess = MutableStateFlow(false)
    private val _proceedWithUpdate = MutableStateFlow(true)
    val proceedWithUpdate = _proceedWithUpdate.asStateFlow()


   override suspend fun getRandomThreeUsersImageUrls(): Flow<Response<List<String>>> {

        return flow {
            val result = safeApiCall(context) {
                val usersCollection = firebaseFirestore.collection("users")
                // 1. Fetch all user documents (or limit to a subset for performance reasons)
                val querySnapshot = usersCollection.get().await()
                val users = querySnapshot.documents

                val randomUsers = if (users.size <= 3) users else users.shuffled().take(3)

                // 4. Extract the imageUrl field from each selected user
                val imageUrls = randomUsers.mapNotNull { document ->
                    document.getString("profileImage")
                }.toMutableList()

                while (imageUrls.size < 3) {
                    imageUrls.add("") // Add empty fields to reach a size of 3
                }

                imageUrls
            }
            emit(result)
        }.catch {
            emit(Response.Error("SomeThing went wrong $it"))
        }
    }



    override suspend fun userExists(email: String , navController: NavController): Flow<Response<Boolean>> {
        return flow {
            var password = ""
            var isThirdParty = true
            val response = safeApiCall(context) {
                val response = safeApiCall(context) {
                    val usersCollection = firebaseFirestore.collection("users")
                    val emailC = usersCollection.whereEqualTo("email", email).get().await()
                    if (!emailC.isEmpty)
                    {
                        val userdoc = emailC.documents.first()
                        password = userdoc.getString("password").toString()
                        isThirdParty = userdoc.getBoolean("_third_party") == true
                    }
                    !emailC.isEmpty
                }

                if (response is Response.Success) {
                    if (response.data && password.isNotEmpty() && !isThirdParty) {
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
private suspend fun changeUserPassword(newPassword: String): Boolean {
    return try {
        val user = FirebaseAuth.getInstance().currentUser
        user?.updatePassword(newPassword)?.await() // Await to ensure completion
        Log.d("PasswordUpdate", "Password updated successfully.")
        true
    } catch (e: Exception) {
        Log.e("PasswordUpdate", "Error updating password: ${e.message}")
        false
    }
}

    private suspend fun reAuthenticateUser(email: String, password: String): Result<String> {
        return try {
            val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
            Result.success(authResult.user?.uid ?: "")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    // Main function to update user data
    override suspend fun updateUserData(userData: UserData): Flow<Response<Boolean>> = flow {
        try {
            emit(Response.Loading)
            // Reset the proceedWithUpdate state
            _proceedWithUpdate.value = true

            if (!userData.thirdParty) {
                val reAuthResult = reAuthenticateUser(
                    sharedPrefs.getString(Constant.EMAIL) ?: "",
                    sharedPrefs.getString(Constant.PASSWORD) ?: ""
                )

                // Handle the result
                reAuthResult.fold(
                    onSuccess = { userId ->
                        Log.d("reAuth", "Success: true, User ID: $userId")
                    },
                    onFailure = { exception ->
                        emit(Response.Error(exception.message ?: "Re-authentication failed"))
                        _proceedWithUpdate.value = false // Prevent further execution
                    }
                )

                // Update password
                if (_proceedWithUpdate.value) {
                    Log.d("PasswordUpdate", "Attempting to change password.")
                    val passwordSuccess = changeUserPassword(userData.password)
                    _passwordUpdateSuccess.value = passwordSuccess
                    if (!passwordSuccess) {
                        Log.d("PasswordUpdate", "Password update failed.")
                        emit(Response.Error("Password update failed"))
                        _proceedWithUpdate.value = false // Prevent further execution
                    } else {
                        Log.d("PasswordUpdate", "Password updated successfully.")
                    }
                }
            }

            // Check if we can proceed with the Firestore update
            if (_proceedWithUpdate.value && _passwordUpdateSuccess.value) {
                val result = updateFirestore(userData)
                emit(result)
                Toast.makeText(context,"data Uploaded successfully",Toast.LENGTH_SHORT).show()
            } else {
                Log.d("UpdateUserData", "Skipping Firestore update due to password failure.")
                emit(Response.Error("Password update failed, skipping Firestore update"))
            }
        } catch (e: Exception) {
            Log.e("UpdateUserDataError", e.message ?: "An unknown error occurred")
            emit(Response.Error(e.message ?: "An unknown error occurred"))
        }
    }

    private suspend fun updateFirestore(userData: UserData): Response<Boolean> {
        // Prepare data for Firestore update
        Log.d("name", userData.name)
        Log.d("password", userData.password)
        val updatedData: MutableMap<String, Any> = mutableMapOf(
            "name" to userData.name
        )
        if (!userData.thirdParty) {
            updatedData["password"] = userData.password
        }

        return try {
            // Update Firestore document
            Log.d("FirestoreUpdate", "Attempting to update Firestore for user: ${userData.vid} with data: $updatedData")
            val firestoreUpdate = firebaseFirestore.collection("users").document(userData.vid).update(updatedData)
            firestoreUpdate.await() // Use await() to wait for completion
            Log.d("FirestoreUpdate", "Firestore update successful for user: ${userData.vid}")

            // Update shared preferences
            sharedPrefs.save(Constant.NAME, userData.name)
            if (!userData.thirdParty) {
                sharedPrefs.save(Constant.PASSWORD, userData.password)
            }

            Response.Success(true)
        } catch (e: Exception) {
            Log.e("FirestoreUpdateError", e.message ?: "An error occurred while updating Firestore")
            Response.Error(e.message ?: "An error occurred while updating Firestore")
        }
    }



    override suspend fun updateSettingData(userData: UserData): Flow<Response<Boolean>> {
        return flow<Response<Boolean>> {
            val response = safeApiCall(context){
                val userRef = firebaseFirestore.collection("users").document(sharedPrefs.getString(Constant.USER_ID) ?: "")
                userRef.set(userData).await()
                sharedPrefs.saveSetting(userData.settingData)
                true
            }
            emit(response)
        }.catch {
            emit(Response.Error(it.message ?: "An unknown error occurred"))
        }
    }

    suspend fun deleteImageByUrl(imageUrl: String): Boolean {
        return try {
            val storageRef = firebaseStorage.getReferenceFromUrl(imageUrl)
            storageRef.delete().await() // suspend function with await for coroutines
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun uploadNewImage(imageUri: String): String {
        return try {
            // Assume you're using Firebase Storage
            val storageRef = FirebaseStorage.getInstance().reference.child("avatars/${sharedPrefs.getString(Constant.USER_ID)}.jpg")
            val uploadTask = storageRef.putFile(Uri.parse(imageUri)).await() // Upload the new image
            val downloadUrl = storageRef.downloadUrl.await() // Get the download URL
            downloadUrl.toString() // Return the URL of the uploaded image
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    suspend fun updateUserImageInDatabase(newImageUrl: String): Boolean {
        return try {
            // Update the user's profile with the new image URL
            val userId = sharedPrefs.getString(Constant.USER_ID)?:""
            val dbRef = FirebaseFirestore.getInstance().collection("users").document(userId)
            dbRef.update("profileImage", newImageUrl).await()
            sharedPrefs.save(Constant.IMAGE_URL,newImageUrl)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }



    override suspend fun updateAvtar(imageUri: String): Flow<Response<Boolean>> = flow {
        try {
                // 1. Delete previous image by URL (if available)
                val previousImageUrl = sharedPrefs.getString(Constant.IMAGE_URL) // Assuming you have a way to get the current image URL
                if (previousImageUrl!!.isNotEmpty()) {
                    val deleteResult =
                        deleteImageByUrl(previousImageUrl) // Call a function to delete the old image
                    if (!deleteResult) {
                        emit(Response.Error("Failed to delete the previous image"))
                        return@flow
                    }
                }
                // 2. Upload new image
                val newImageUrl = uploadNewImage(imageUri) // Function to upload the new image and get its URL
                if (newImageUrl.isNotEmpty()) {
                    val updateResult = updateUserImageInDatabase(newImageUrl)
                    if (updateResult) {
                        emit(Response.Success(true)) // Emit success if all operations succeed
                    } else {
                        emit(Response.Error("Failed to update user profile with the new image"))
                    }
                } else {
                    emit(Response.Error("Failed to upload the new image"))
                }

        } catch (e: Exception) {
            emit(Response.Error("An error occurred: ${e.localizedMessage}"))
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