package com.Singlee.forex.screens.Home.nav

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.Singlee.forex.graph.HomeRoutes
import com.Singlee.forex.screens.Home.AllSignalScreen
import com.Singlee.forex.screens.Home.ProfileScreen
import com.Singlee.forex.screens.Home.ReportScreen
import com.Singlee.forex.screens.Home.ViewModels.SignalVideoModel
import com.Singlee.forex.screens.Home.StartChat
import com.Singlee.forex.screens.Home.ViewModels.UserViewModel
import com.Singlee.forex.ui.theme.extra_light


val currentRoute=   mutableStateOf(HomeRoutes.HomeRoute.route)

@Composable
fun NavScreen(navController: NavHostController, signalViewModel: SignalVideoModel)
{

    val userViewModel : UserViewModel = hiltViewModel()



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
            HomeRoutes.HomeRoute.route -> AllSignalScreen(
                navController = navController,
                signalViewModel = signalViewModel
            )
            HomeRoutes.StartChatRoute.route -> StartChat(
                navController = navController,
                userViewModel = userViewModel
            )
            HomeRoutes.ReportScreen.route -> ReportScreen(
                navController = navController,
                signalViewModel = signalViewModel
            )
            HomeRoutes.ProfileScreen.route -> ProfileScreen(
                navController = navController,
                userViewModel = userViewModel
            )
        }

    }

}