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
    navigation( startDestination = AuthRouts.LoginRoute.route , route = NavRouts.AuthRoute.route)
    {
        composable(AuthRouts.LoginRoute.route)
        {
            loginScreen(navController , authViewModel)
        }
        composable(AuthRouts.RegisterRoute.route)
        {
            signUpScreen(navController , authViewModel)
        }
        composable(AuthRouts.ForgetPassRoute.route)
        {
            forgetPasswordScreen(navController,  authViewModel)
        }

        composable("${AuthRouts.OTPRoute.route}/{email}/{password}")
        {
            val email = it.arguments?.getString("email")
            val password = it.arguments?.getString("password")
            otpScreem(navController , email , password)
        }
        composable("${AuthRouts.NewPassRoute.route}/{email}/{password}")
        {
            val email = it.arguments?.getString("email")
            val password = it.arguments?.getString("password")
            newPasScreen(navController,  authViewModel,email , password)
        }

    }
}
