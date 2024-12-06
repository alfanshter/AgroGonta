package com.ptpws.agrogontafarm.ui.auth

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ptpws.agrogontafarm.ui.AppScreen

@Composable
fun AuthNavHost(navController: NavHostController,initialRoute : String) {


    NavHost(
        navController = navController,
        startDestination = initialRoute ?: AppScreen.Auth.Splash.route
    ) {
        composable(AppScreen.Auth.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(AppScreen.Auth.Login.route) {
            LoginScreen(navController = navController, hiltViewModel())
        }

    }
}