package com.Singlee.forex.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.Singlee.forex.screens.Auth.AuthViewModel
import com.Singlee.forex.screens.Home.AboutSinglee
import com.Singlee.forex.screens.Home.AllSignalScreen
import com.Singlee.forex.screens.Home.ChatScreen
import com.Singlee.forex.screens.Home.PremiumPlan
import com.Singlee.forex.screens.Home.PrivacyPolicy
import com.Singlee.forex.screens.Home.ProfileScreen
import com.Singlee.forex.screens.Home.ProfileSettingScreen
import com.Singlee.forex.screens.Home.ReportScreen
import com.Singlee.forex.screens.Home.SignalDetail
import com.Singlee.forex.screens.Home.StartChat
import com.Singlee.forex.screens.Home.ViewModels.UserViewModel
import com.Singlee.forex.screens.Home.nav.NavScreen
import com.Singlee.forex.screens.Home.Prefrences
import com.Singlee.forex.screens.Home.ViewModels.ChatViewModel
import com.Singlee.forex.screens.Home.ViewModels.SignalVideoModel

fun NavGraphBuilder.homeNav(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    signalViewModel: SignalVideoModel,
    messageViewModel: ChatViewModel
)
{
    navigation(startDestination = HomeRoutes.NavScreen.route , route = NavRouts.AppRoute.route)
    {
        composable(HomeRoutes.HomeRoute.route)
        {
            AllSignalScreen(navController , signalViewModel)
        }
        composable(HomeRoutes.PremiumRoute.route)
        {
            PremiumPlan(navController)
        }
        composable(HomeRoutes.StartChatRoute.route)
        {
            StartChat(navController)
        }
        composable(HomeRoutes.ChatScreenRoute.route)
        {
            ChatScreen(messageViewModel)
        }
        composable(HomeRoutes.ReportScreen.route)
        {
            ReportScreen(navController , signalViewModel)
        }
        composable(HomeRoutes.ProfileScreen.route)
        {
            ProfileScreen(navController , userViewModel)
        }
        composable(HomeRoutes.NavScreen.route)
        {
            NavScreen(navController , signalViewModel)
        }
        composable(HomeRoutes.ProfileSettingScreen.route)
        {
            ProfileSettingScreen(userViewModel)
        }
        composable(HomeRoutes.PreferencesScreen.route)
        {
            Prefrences(userViewModel)
        }
        composable(HomeRoutes.AboutSinglee.route)
        {
            AboutSinglee()
        }
        composable(HomeRoutes.PrivacyPolicy.route)
        {
            PrivacyPolicy()
        }
        composable(HomeRoutes.SignalDetail.route)
        {
            SignalDetail()
        }
    }
}