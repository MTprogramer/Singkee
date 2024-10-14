package com.Singlee.forex.graph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.Singlee.forex.screens.Auth.AuthViewModel

@Composable
fun Nav(loggedIn: Boolean)
{
    val authViewModel : AuthViewModel = hiltViewModel()

    // Check if the user is logged in
    val startDestination = if (loggedIn) {
        NavRouts.AppRoute.route // Navigate to the home route
    } else {
        NavRouts.AuthRoute.route // Navigate to the authentication route
    }


    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        authNav(navController , authViewModel)
        homeNav(navController)
    }

}