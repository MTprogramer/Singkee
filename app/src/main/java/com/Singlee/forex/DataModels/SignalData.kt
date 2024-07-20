package com.Singlee.forex.DataModels

data class SignalData(
     val buy : Boolean = false,
     val isPremium : Boolean = false,
     val entry : Double = 0.0,
     val tp : Double = 0.0,
     val sl : Double = 0.0,
    val pairName : String
)