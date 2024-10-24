package com.Singlee.forex.DataModels

data class SettingData(
    val signal : Boolean = false,
    val chat_Notifications : Boolean = false,
    val support_Notifications : Boolean = true,
    val progress_Notifications : Boolean = false,
    val offer_Notifications : Boolean = true,
    val team_Notifications : Boolean = true
){
    fun toMap(): Map<String, Any> {
        return mapOf(
            "signal" to signal,
            "chat_Notifications" to chat_Notifications,
            "support_Notifications" to support_Notifications,
            "progress_Notifications" to progress_Notifications,
            "offer_Notifications" to offer_Notifications,
            "team_Notifications" to team_Notifications
        )
    }
}
