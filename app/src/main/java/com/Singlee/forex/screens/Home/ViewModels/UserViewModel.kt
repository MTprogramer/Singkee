package com.Singlee.forex.screens.Home.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Singlee.forex.DataModels.SettingData
import com.Singlee.forex.DataModels.UserData
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.Repo.User.UserRepo
import com.Singlee.forex.Utils.SharedPrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepo: UserRepo,
    private val sharedPrefs: SharedPrefs
) : ViewModel()
{

    private val _userDataResult = MutableStateFlow<Response<UserData>>(Response.Empty)
    val userDataresult: Flow<Response<UserData>> = _userDataResult
    private val _profileSetting = MutableStateFlow<Response<SettingData>>(Response.Empty)
    val profileSetting: Flow<Response<SettingData>> = _profileSetting
    private val _userDataUpdate = MutableStateFlow<Response<Boolean>>(Response.Empty)
    val userDataUpdate: Flow<Response<Boolean>> = _userDataUpdate

    fun updateUserData(userData: UserData) = viewModelScope.launch {
        _userDataUpdate.value = Response.Loading
        userRepo.updateUserData(userData).collect{result ->
            _userDataUpdate.value = result
        }
    }

    fun updateSettingData(userData: SettingData) = viewModelScope.launch {
        _userDataUpdate.value = Response.Loading
        userRepo.updateSettingData(userData).collect{result ->
            _userDataUpdate.value = result
        }
    }

    fun signout() = viewModelScope.launch {
        userRepo.logout()
    }

    fun getUserData() = viewModelScope.launch {
        _userDataResult.value = Response.Loading
        try {
            val userData = sharedPrefs.getUser()
            _userDataResult.value = Response.Success(userData)
        } catch (e: Exception) {
            _userDataResult.value = Response.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    fun getSettingData() = viewModelScope.launch {
        _profileSetting.value = Response.Loading
        try {
            val userData = sharedPrefs.getProfilePref()
            _profileSetting.value = Response.Success(userData)
        } catch (e: Exception) {
            _profileSetting.value = Response.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}