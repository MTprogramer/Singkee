package com.Singlee.forex.Repo.Messages

import com.Singlee.forex.DataModels.Message
import com.Singlee.forex.Repo.Response
import kotlinx.coroutines.flow.Flow

interface MessageRepo
{
    suspend fun sendMessage(message: Message) : Flow<Response<Boolean>>
    suspend fun getMessages(): Flow<Response<List<Message>>>

}