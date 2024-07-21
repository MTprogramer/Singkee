package com.Singlee.forex.graph


sealed class HomeRoutes {
    data object HomeRoute : NavRouts("home")
    data object PremiumRoute : NavRouts("premium")
//    data object RegisterRoute : NavRouts("register")
//    data object ForgetPassRoute : NavRouts("forgetPass")
//    data object OTPRoute : NavRouts("otp")
//    data object NewPassRoute : NavRouts("newPass")
}