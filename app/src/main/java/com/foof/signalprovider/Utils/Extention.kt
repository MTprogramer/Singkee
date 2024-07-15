package com.foof.signalprovider.Utils

import android.text.TextUtils
import android.util.Patterns
import kotlin.random.Random

fun hasCapitalLetter(str: String): Boolean {
    return str.contains(Regex("[A-Z]"))
}
fun hasNumber(str: String): Boolean {
    return str.any { it.isDigit() }
}
fun hasSpecialCharacter(str: String): Boolean {
    return str.contains(Regex("[^a-zA-Z0-9\\s]"))
}


fun String.isValidEmail(): Boolean {
    return !(TextUtils.isEmpty(this) || !Patterns.EMAIL_ADDRESS.matcher(this).matches())
}

fun generateSixDigitCode(): String {
    val randomNumber = Random.nextInt(100000, 999999)
    return randomNumber.toString()
}