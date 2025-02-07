package com.Singlee.forex.graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.Singlee.forex.DataModels.SignalData
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
import com.google.gson.Gson

@RequiresApi(Build.VERSION_CODES.O)
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
            StartChat(navController , userViewModel)
        }
        composable(HomeRoutes.ChatScreenRoute.route)
        {
            ChatScreen(messageViewModel , navController)
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
            ProfileSettingScreen(userViewModel , navController)
        }
        composable(HomeRoutes.PreferencesScreen.route)
        {
            Prefrences(userViewModel , navController)
        }
        composable(HomeRoutes.AboutSinglee.route)
        {
            AboutSinglee(navController)
        }
        composable(HomeRoutes.PrivacyPolicy.route)
        {
            PrivacyPolicy(navController)
        }
        composable(
            route= "${HomeRoutes.SignalDetail.route}/{signalData}",
            arguments = listOf(navArgument("signalData") { type = NavType.StringType })
        )
        { backStackEntry ->
            val signalDataJson = backStackEntry.arguments?.getString("signalData") ?: return@composable
            val signalData = Gson().fromJson(signalDataJson, SignalData::class.java)
            SignalDetail(signalData ,navController)
        }
    }
}