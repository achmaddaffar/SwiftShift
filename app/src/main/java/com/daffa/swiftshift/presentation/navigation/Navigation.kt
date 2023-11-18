package com.daffa.swiftshift.presentation.navigation

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.daffa.swiftshift.presentation.features.home.HomeScreen
import com.daffa.swiftshift.presentation.features.login.LoginScreen
import com.daffa.swiftshift.presentation.features.onboarding.OnBoardingScreen
<<<<<<< HEAD
import com.daffa.swiftshift.presentation.features.profile.ProfileScreen
=======
import com.daffa.swiftshift.presentation.features.register.RegisterScreen
>>>>>>> fc9b81834d6f90671207cf7f3d32c260865c4ab5
import com.daffa.swiftshift.presentation.features.splash.SplashScreen

@Composable
fun Navigation(
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
<<<<<<< HEAD
        startDestination = Screen.ProfileScreen.route,
=======
        startDestination = Screen.LoginScreen.route,
>>>>>>> fc9b81834d6f90671207cf7f3d32c260865c4ab5
        modifier = modifier
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.OnBoardingScreen.route) {
            OnBoardingScreen(navController = navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController = navController)
        }
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController, scaffoldState = scaffoldState)
        }
        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
    }
}