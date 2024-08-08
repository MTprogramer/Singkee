package com.Singlee.forex.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.Singlee.forex.screens.Home.AboutSinglee
import com.Singlee.forex.screens.Home.AllSignalScreen
import com.Singlee.forex.screens.Home.ChatScreen
import com.Singlee.forex.screens.Home.PremiumPlan
import com.Singlee.forex.screens.Home.PrivacyPolicy
import com.Singlee.forex.screens.Home.ProfileScreen
import com.Singlee.forex.screens.Home.ProfileSettingScreen
import com.Singlee.forex.screens.Home.ReportScreen
import com.Singlee.forex.screens.Home.StartChat
import com.Singlee.forex.screens.Home.nav.NavScreen
import com.Singlee.forex.screens.Home.nav.Prefrences

fun NavGraphBuilder.homeNav(navController: NavHostController)
{
    navigation(startDestination = HomeRoutes.PrivacyPolicy.route , route = NavRouts.AppRoute.route)
    {
        composable(HomeRoutes.HomeRoute.route)
        {
            AllSignalScreen(navController)
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
            ChatScreen(navController)
        }
        composable(HomeRoutes.ReportScreen.route)
        {
            ReportScreen(navController)
        }
        composable(HomeRoutes.ProfileScreen.route)
        {
            ProfileScreen(navController)
        }
        composable(HomeRoutes.NavScreen.route)
        {
            NavScreen(navController)
        }
        composable(HomeRoutes.ProfileSettingScreen.route)
        {
            ProfileSettingScreen()
        }
        composable(HomeRoutes.PreferencesScreen.route)
        {
            Prefrences()
        }
        composable(HomeRoutes.AboutSinglee.route)
        {
            AboutSinglee()
        }
        composable(HomeRoutes.PrivacyPolicy.route)
        {
            PrivacyPolicy()
        }
    }
}