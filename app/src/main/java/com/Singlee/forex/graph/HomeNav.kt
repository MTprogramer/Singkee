package com.Singlee.forex.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.Singlee.forex.screens.Home.AllSignalScreen
import com.Singlee.forex.screens.Home.PremiumPlan

fun NavGraphBuilder.homeNav(navController: NavHostController)
{
    navigation(startDestination = HomeRoutes.PremiumRoute.route , route = NavRouts.AppRoute.route)
    {
        composable(HomeRoutes.HomeRoute.route)
        {
            AllSignalScreen(navController)
        }
        composable(HomeRoutes.PremiumRoute.route)
        {
            PremiumPlan()
        }
    }
}