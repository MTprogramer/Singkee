package com.Singlee.forex.screens.Auth

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.credentials.GetCredentialRequest
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.Singlee.forex.DataModels.UserData
import com.Singlee.forex.Repo.Auth.AuthRepository
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.Repo.User.UserRepo
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authenticationRepository: AuthRepository,
    private val userRepo: UserRepo
) : ViewModel() {
    private val _registrationStatus = MutableStateFlow<Response<AuthResult>>(Response.Empty)
    val registrationStatus: Flow<Response<AuthResult>> = _registrationStatus

    private val _signInStatus = MutableStateFlow<Response<UserData>>(Response.Empty)
    val signInStatus: Flow<Response<UserData>> = _signInStatus

    private val _resetPassword = MutableStateFlow<Response<String>>(Response.Empty)
    val resetPassword: Flow<Response<String>> = _resetPassword

    private val _userExist = MutableStateFlow<Response<Boolean>>(Response.Empty)
    val userExist: Flow<Response<Boolean>> = _userExist

    val _googleState = MutableStateFlow<Response<AuthResult>>(Response.Empty)
    val googleState: Flow<Response<AuthResult>> = _googleState



    fun signUp(email: String, password: String, name: String) = viewModelScope.launch {
        _registrationStatus.value = Response.Loading
        authenticationRepository.registerUser(email, password, name).collect { result ->
            _registrationStatus.value = result
        }
    }

    fun userExist(email: String, navController: NavHostController) = viewModelScope.launch {
        _userExist.value = Response.Loading
        userRepo.userExists(email , navController).collect { result ->
            _userExist.value = result
        }
    }

    fun resetPassword(email: String, userPass: String ,  newPass: String) = viewModelScope.launch {
        _userExist.value = Response.Loading
        authenticationRepository.loginUser(email , userPass).collect{
            when(it)
            {
                is Response.Success ->{
                    userRepo.resetUserPass(it.data , newPass).collect { result ->
                        _resetPassword.value = result
                    }
                }
                Response.Empty -> {}
                is Response.Error -> {_resetPassword.value = Response.Error("Something Wrong")}
                Response.Loading -> {}
            }
        }

    }

    fun signIn(email: String, password: String) = viewModelScope.launch {
        _signInStatus.value = Response.Loading
        authenticationRepository.loginUser(email, password).collect { result ->
            when(result)
            {
                is Response.Success ->{
                    val userId = result.data.user?.uid
                    Log.d("userId",userId.toString())
                    userRepo.getUserData(userId.toString()).collect{
                        _signInStatus.value = it
                    }
                }
                is Response.Empty -> {}
                is Response.Error -> {_signInStatus.value = Response.Error(result.message)}
                is Response.Loading -> {}
            }
        }
    }

    fun googleSignIn(credentialManager: CredentialManager, context: Context, clintID: String) {
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(clintID)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        viewModelScope.launch {
            try {
                val result = credentialManager.getCredential(context, request)
                val credential = result.credential
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val googleIdToken = googleIdTokenCredential.idToken

                val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                googleSignIn(firebaseCredential)
            } catch (e: GetCredentialCancellationException) {
                Log.d("SignIn", "Sign-in cancelled by user")
                // Handle the case where the user cancels the sign-in
            } catch (e: java.util.concurrent.TimeoutException) {
                Log.e("SignIn", "Network timeout", e)
                // Handle network timeout
            } catch (e: java.io.IOException) {
                Log.e("SignIn", "Network error", e)
                // Handle network error
            } catch (e: Exception) {
                Log.e("SignIn", "Sign-in failed", e)
                // Handle other exceptions
            }
        }
    }


    fun googleSignIn(credential: AuthCredential) = viewModelScope.launch {
        authenticationRepository.googleSignIn(credential).collect { result ->
            _registrationStatus.value = result
        }
    }




    val callbackManager: CallbackManager = CallbackManager.Factory.create()

    fun signInWithFacebook(activity: Activity) {

        LoginManager.getInstance().logInWithReadPermissions(activity, listOf("email", "public_profile"))

        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onCancel() {
                Log.d("error","cancel")
            }

            override fun onError(error: FacebookException) {
                Log.d("error",error.message.toString())
            }

            override fun onSuccess(result: LoginResult) {
                handleFacebookAccessToken(result.accessToken)
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken?) {
        val credential = FacebookAuthProvider.getCredential(token?.token!!)
        googleSignIn(credential)

    }

    override fun onCleared() {
        super.onCleared()
        LoginManager.getInstance().unregisterCallback(callbackManager)
    }

}