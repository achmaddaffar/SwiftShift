package com.daffa.swiftshift.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.daffa.swiftshift.presentation.features.history.HistoryScreen
import com.daffa.swiftshift.presentation.features.home.HomeScreen
import com.daffa.swiftshift.presentation.features.login.LoginScreen
import com.daffa.swiftshift.presentation.features.onboarding.OnBoardingScreen
import com.daffa.swiftshift.presentation.features.profile.ProfileScreen
import com.daffa.swiftshift.presentation.features.register.RegisterScreen
import com.daffa.swiftshift.presentation.features.search.SearchScreen
import com.daffa.swiftshift.presentation.features.splash.SplashScreen

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = modifier
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.OnBoardingScreen.route) {
            OnBoardingScreen(navController = navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController, scaffoldState = scaffoldState)
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController, scaffoldState = scaffoldState)
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController, scaffoldState = scaffoldState)
        }
        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController, scaffoldState = scaffoldState)
        }
        composable(Screen.SearchScreen.route) {
            SearchScreen(navController = navController)
        }
        composable(Screen.HistoryScreen.route) {
            HistoryScreen(navController = navController)
        }
    }
}