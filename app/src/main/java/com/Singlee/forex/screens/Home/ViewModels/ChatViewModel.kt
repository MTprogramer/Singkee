package com.Singlee.forex.screens.Home.ViewModels

import android.health.connect.changelog.ChangeLogTokenRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.Singlee.forex.DataModels.Message
import com.Singlee.forex.DataModels.SignalData
import com.Singlee.forex.Repo.Messages.MessageRepo
import com.Singlee.forex.Repo.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val messageRepo: MessageRepo) : ViewModel()
{
    private val _update =  MutableStateFlow<Response<Boolean>>(Response.Empty)
    val update : Flow<Response<Boolean>> = _update

    private val _messages =  MutableStateFlow<Response<List<Message>>>(Response.Empty)
    val messages : Flow<Response<List<Message>>> = _messages


    fun sendMessage(message: Message) = viewModelScope.launch{
        _update.value = Response.Loading
        messageRepo.sendMessage(message).collect{
            _update.value = it
        }
    }

    fun getMessages() = viewModelScope.launch {
        _messages.value = Response.Loading
        messageRepo.getMessages().collect {
            _messages.value = it
        }
    }

}