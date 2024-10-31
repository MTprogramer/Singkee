package com.Singlee.forex.Utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.Singlee.forex.DataModels.SignalData
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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
    val AUTHOR = "AUTHOR"
    val IMAGE_URL = "IMAGE_URL"
    val IS_THIRDPARTY = "IS_THIRDPARTY"

    val MESSAGE_ID = "MESSAGE_ID"

    // user profile setting
    val chat_Notifications = "chat_Notifications"
    val support_Notifications = "support_Notifications"
    val Signal = "signal"
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun <T : Any> groupSignalsByDate(signals: List<T>, timestampExtractor: (T) -> Timestamp?): Map<String, List<T>> {
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)

        return signals.groupBy { signal ->
            val signalDate = timestampExtractor(signal)?.toDate()?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
            when {
                signalDate == today -> "Today"
                signalDate == yesterday -> "Yesterday"
                signalDate != null -> signalDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                else -> "Unknown Date"
            }
        }
    }


//    @RequiresApi(Build.VERSION_CODES.O)
//    fun groupSignalsByDate(signals: List<T>): Map<String, List<T>> {
//        val today = LocalDate.now()
//        val yesterday = today.minusDays(1)
//
//        return signals.groupBy { signal ->
//            val signalDate = signal.timestamp?.toDate()?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
//            when (signalDate) {
//                today -> "Today"
//                yesterday -> "Yesterday"
//                else -> signalDate?.format(DateTimeFormatter.ofPattern("yyyy.MM.dd")) ?: "Unknown Date"
//            }
//        }
//    }

}