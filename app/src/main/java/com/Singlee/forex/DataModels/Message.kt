package com.Singlee.forex.DataModels

data class Message(
    val id: String = "",
    val content: String = "",
    val senderId: String = "",
    val timestamp: Long = System.currentTimeMillis()
)