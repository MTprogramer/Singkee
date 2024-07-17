package com.Singlee.forex.graph

sealed class NavRouts(val route : String)
{
    data object AuthRoute:NavRouts("auth")
    data object AppRoute:NavRouts("app")
}
