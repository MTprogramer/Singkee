package com.Singlee.forex.DataModels

data class SettingData(
    val signal : Boolean = false,
    val chat_Notifications : Boolean = false,
    val support_Notifications : Boolean = true,
    val progress_Notifications : Boolean = false,
    val offer_Notifications : Boolean = true,
    val team_Notifications : Boolean = true
)
