package com.Singlee.forex.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.Singlee.forex.screens.Home.AllSignalScreen

fun NavGraphBuilder.homeNav(navController: NavHostController)
{
    navigation(startDestination = HomeRoutes.HomeRoute.route , route = NavRouts.AppRoute.route)
    {
        composable(HomeRoutes.HomeRoute.route)
        {
            AllSignalScreen(navController)
        }
    }
}