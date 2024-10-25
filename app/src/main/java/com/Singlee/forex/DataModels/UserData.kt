package com.Singlee.forex.DataModels

data class UserData(
    val name : String = "",
    val email : String = "",
    val password : String = "",
    val vid : String  = "",
    val profileImage : String = "" ,
    val subscription : Boolean = false,
    val is_third_party : Boolean = false,
    val author : Boolean = false,
    val chatBanned : Boolean = false,
    val bannedReason : String = "",
    val settingData: SettingData = SettingData()
)
