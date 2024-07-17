package com.Singlee.forex.Repo

import android.content.Context
import com.Singlee.forex.Utils.NetworkUtil

sealed class Response<out T> {
    data object Empty : Response<Nothing>()
    data object Loading : Response<Nothing>()
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val message: String) : Response<Nothing>()
}


suspend fun <T> safeApiCall(
    context: Context,
    apiCall: suspend () -> T
): Response<T> {
    return if (NetworkUtil.isInternetAvailable(context)) {
        try {
            Response.Success(apiCall.invoke())
        } catch (exception: Exception) {
            Response.Error(exception.message ?: "Unknown error occurred")
        }
    } else {
        Response.Error("No internet connection")
    }
}