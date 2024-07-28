package com.Singlee.forex.screens

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.Singlee.forex.graph.HomeRoutes
import com.Singlee.forex.screens.Home.AllSignalScreen
import com.Singlee.forex.screens.Home.ProfileScreen
import com.Singlee.forex.screens.Home.ReportScreen
import com.Singlee.forex.screens.Home.StartChat
import com.Singlee.forex.ui.theme.blue
import com.Singlee.forex.ui.theme.extra_light
import com.Singlee.forex.ui.theme.red
import com.google.android.play.integrity.internal.c


val currentRoute=   mutableStateOf(HomeRoutes.HomeRoute.route)

@Composable
fun NavScreen(navController: NavHostController)
{



    Scaffold(
        bottomBar = {
            CustomBottomNavigation(
                selectedRoute = currentRoute.value,
                onItemSelected = {
                    currentRoute.value=it
                }
            )
        },
        containerColor = Color.Transparent,
        contentColor = contentColorFor(backgroundColor = extra_light)

    )
    {PaddingValues ->

        Log.d("route",currentRoute.value)
        when(currentRoute.value)
        {
            HomeRoutes.HomeRoute.route -> AllSignalScreen(navController = navController)
            HomeRoutes.StartChatRoute.route -> StartChat(navController = navController)
            HomeRoutes.ReportScreen.route -> ReportScreen(navController = navController)
            HomeRoutes.ProfileScreen.route -> ProfileScreen(navController = navController)
        }

    }

}