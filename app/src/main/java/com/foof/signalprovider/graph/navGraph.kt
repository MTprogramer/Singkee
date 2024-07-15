package com.foof.signalprovider.graph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.foof.signalprovider.screens.Auth.AuthViewModel

@Composable
fun Nav()
{
    val authViewModel : AuthViewModel = hiltViewModel()

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRouts.AuthRoute.route) {
        authNav(navController , authViewModel)
    }

}