package com.Singlee.forex.screens.Home.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Singlee.forex.DataModels.SignalData
import com.Singlee.forex.Repo.Response
import com.Singlee.forex.Repo.Signal.SignalsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignalVideoModel @Inject constructor(val signalRepo: SignalsRepo) : ViewModel()
{
    private val _signals =  MutableStateFlow<Response<List<SignalData>>>(Response.Empty)
    val signals : Flow<Response<List<SignalData>>> = _signals


    fun getActiveSignals(isActive: Boolean) = viewModelScope.launch{
        _signals.value = Response.Loading
        signalRepo.getActiveSignals(isActive).collect{
            _signals.value = it
        }
    }

}