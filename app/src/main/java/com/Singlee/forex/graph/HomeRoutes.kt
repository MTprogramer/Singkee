package com.Singlee.forex.graph


sealed class HomeRoutes {
    data object HomeRoute : NavRouts("home")
    data object PremiumRoute : NavRouts("premium")
    data object StartChatRoute : NavRouts("startChat")
    data object ChatScreenRoute : NavRouts("ChatScreen")
    data object ReportScreen : NavRouts("reportScreen")
    data object ProfileScreen : NavRouts("ProfileScreen")
    data object NavScreen : NavRouts("NavScreen")
    data object ProfileSettingScreen : NavRouts("ProfileSettingScreen")
    data object PreferencesScreen : NavRouts("PreferencesScreen")
    data object AboutSinglee : NavRouts("AboutSinglee")
    data object PrivacyPolicy : NavRouts("PrivacyPolicy")
    data object SignalDetail : NavRouts("SignalDetail")
//    data object RegisterRoute : NavRouts("register")
//    data object ForgetPassRoute : NavRouts("forgetPass")
//    data object OTPRoute : NavRouts("otp")
//    data object NewPassRoute : NavRouts("newPass")
}