package com.Singlee.forex.DataModels

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class Message(
    val messageId: String = "",
    val content: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val profileImage: String = "",
    val author : Boolean = false,
    @PropertyName("media_linked") val isMedia_linked: Boolean = false,
    @PropertyName("sent") val isSent: Boolean = false,
    val timestamp: Timestamp = Timestamp.now()
)