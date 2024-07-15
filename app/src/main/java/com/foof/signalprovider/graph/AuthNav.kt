package com.foof.signalprovider.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.foof.signalprovider.screens.Auth.AuthViewModel
import com.foof.signalprovider.screens.Auth.forgetPasswordScreen
import com.foof.signalprovider.screens.Auth.loginScreen
import com.foof.signalprovider.screens.Auth.newPasScreen
import com.foof.signalprovider.screens.Auth.otpScreem
import com.foof.signalprovider.screens.Auth.signUpScreen

fun NavGraphBuilder.authNav(navController: NavHostController, authViewModel: AuthViewModel)
{

    navigation( startDestination = "login" , route = NavRouts.AuthRoute.route)
    {
        composable("login")
        {
            loginScreen(navController , authViewModel)
        }
        composable("signUp")
        {
            signUpScreen(navController , authViewModel)
        }
        composable("forgetPass")
        {
            forgetPasswordScreen(navController , authViewModel)
        }
        composable("otp/{email}")
        {
            val email = it.arguments?.getString("email")
            otpScreem(navController , email)
        }
        composable("newPass")
        {
            newPasScreen(navController)
        }
    }
}
