package com.Singlee.forex.DataModels

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class SignalData(
     val id: String = "",
     val cate: String = "",
     val entry: String = "",
     val sl: String = "",
     val type: String = "",
     val closing: String = "",
     val imageURL : String = "",
     val pips : String = "",
     val hit_tp : Boolean = false,
     val timestamp: Timestamp? = null,
     @PropertyName("premium") val isPremium : Boolean = false,
     val isActive: Boolean = false,
     val tp_list: List<String> = emptyList() // List of strings
)