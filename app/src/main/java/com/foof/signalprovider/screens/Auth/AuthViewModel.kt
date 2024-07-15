package com.foof.signalprovider.screens.Auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _signOutStatus = MutableStateFlow<Response<String>>(Response.Empty)
    val signOutStatus: Flow<Response<String>> = _signOutStatus

    private val _userExist = MutableStateFlow<Response<Boolean>>(Response.Empty)
    val userExist: Flow<Response<Boolean>> = _userExist


    fun signUp(email: String, password: String, name: String) = viewModelScope.launch {
        _registrationStatus.value = Response.Loading
        authenticationRepository.registerUser(email, password, name).collect { result ->
            _registrationStatus.value = result
        }
    }

    fun userExist(email: String) = viewModelScope.launch {
        _userExist.value = Response.Loading
        userRepo.userExists(email).collect { result ->
            _userExist.value = result
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