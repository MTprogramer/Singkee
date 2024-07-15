package com.foof.signalprovider.screens.Auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.foof.signalprovider.DataModels.UserData
import com.foof.signalprovider.Repo.Auth.AuthRepository
import com.foof.signalprovider.Repo.Response
import com.foof.signalprovider.Repo.User.UserRepo
import com.google.firebase.auth.AuthResult
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

}