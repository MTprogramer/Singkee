package com.Singlee.forex.Utils

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Constant
{
    const val clint_ID ="824064639633-prdspjjem1gfg1cnshhhk2k72ht04tfb.apps.googleusercontent.com"

    //user data
    val LOGGED_IN = "LOGGED_IN"
    val SUBSCRIBED = "SUBSCRIBED"
    val USER_ID = "USER_ID"
    val NAME = "NAME"
    val EMAIL = "EMAIL"
    val PASSWORD = "PASSWORD"
val IMAGE_URL = "IMAGE_URL"
    val IS_THIRDPARTY = "IS_THIRDPARTY"

    val MESSAGE_ID = "MESSAGE_ID"

    // user profile setting
    val chat_Notifications = "chat_Notifications"
    val support_Notifications = "support_Notifications"
    val progress_Notifications = "progress_Notifications"
    val offer_Notifications = "offer_Notifications"
    val team_Notifications = "team_Notifications"

    val SignalCon = "SignalCon"
    val Chat = "Chat"




    var userID : String? = null

    fun TimestampToString(timestamp: Timestamp): String {
        val date: Date = timestamp.toDate()

        val dateFormat = SimpleDateFormat("yyyy.MM.dd, HH:mm", Locale.getDefault())

        return dateFormat.format(date)
    }

}