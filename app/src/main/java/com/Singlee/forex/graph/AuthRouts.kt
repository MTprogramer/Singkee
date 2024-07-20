package com.Singlee.forex.graph


sealed class AuthRouts {
    data object LoginRoute : NavRouts("login")
    data object RegisterRoute : NavRouts("register")
    data object ForgetPassRoute : NavRouts("forgetPass")
    data object OTPRoute : NavRouts("otp")
    data object NewPassRoute : NavRouts("newPass")
}