package com.Singlee.forex.screens.Home.nav

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.Singlee.forex.graph.HomeRoutes
import com.Singlee.forex.screens.Home.AllSignalScreen
import com.Singlee.forex.screens.Home.ProfileScreen
import com.Singlee.forex.screens.Home.ReportScreen
import com.Singlee.forex.screens.Home.StartChat
import com.Singlee.forex.ui.theme.extra_light


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

        Log.d("route", currentRoute.value)
        when(currentRoute.value)
        {
            HomeRoutes.HomeRoute.route -> AllSignalScreen(navController = navController)
            HomeRoutes.StartChatRoute.route -> StartChat(navController = navController)
            HomeRoutes.ReportScreen.route -> ReportScreen(navController = navController)
            HomeRoutes.ProfileScreen.route -> ProfileScreen(navController = navController)
        }

    }

}