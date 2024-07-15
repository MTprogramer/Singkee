package com.foof.signalprovider.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.Navigation
import androidx.navigation.compose.navigation

fun NavGraphBuilder.homeNav()
{
    navigation(startDestination = NavRouts.AppRoute.route , route = NavRouts.AppRoute.route)
    {

    }
}