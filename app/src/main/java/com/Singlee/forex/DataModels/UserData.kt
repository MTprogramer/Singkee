package com.Singlee.forex.DataModels

data class UserData(
    val name : String = "",
    val email : String = "",
    val password : String = "",
    val vid : String  = "",
    val profileImage : String = "" ,
    val subscription : Boolean = false
)
