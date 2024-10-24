package com.Singlee.forex.Repo.Signal

import com.Singlee.forex.DataModels.SignalData
import com.Singlee.forex.Repo.Response
import kotlinx.coroutines.flow.Flow

interface SignalsRepo
{
    suspend fun getActiveSignals(isActive : Boolean): Flow<Response<List<SignalData>>>
}