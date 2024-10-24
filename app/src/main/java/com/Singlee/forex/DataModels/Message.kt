package com.Singlee.forex.DataModels

import com.google.firebase.firestore.PropertyName

data class Message(
    val messageId: String = "",
    val content: String = "",
    val senderId: String = "",
    val profileImage : String = "",
    @PropertyName("media_linked") val isMedia_linked : Boolean = false,
    @PropertyName("sent") val isSent : Boolean = false,
    val timestamp: Long = com.google.firebase.Timestamp.now().seconds
)