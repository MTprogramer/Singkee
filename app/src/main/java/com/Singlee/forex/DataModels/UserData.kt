package com.Singlee.forex.DataModels

import com.google.firebase.firestore.PropertyName

data class UserData(
    val name : String = "",
    val email : String = "",
    val password : String = "",
    val vid : String  = "",
    val profileImage : String = "",
    val subscription : Boolean = false,
    @PropertyName("thirdParty") val thirdParty : Boolean = false,
    val author : Boolean = false,
    val chatBanned : Boolean = false,
    val bannedReason : String = "",
    val settingData: SettingData = SettingData()
)
