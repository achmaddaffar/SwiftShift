package com.daffa.swiftshift.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.daffa.swiftshift.presentation.features.gig_detail.GigDetailScreen
import com.daffa.swiftshift.presentation.features.history.HistoryScreen
import com.daffa.swiftshift.presentation.features.home.HomeScreen
import com.daffa.swiftshift.presentation.features.login.LoginScreen
import com.daffa.swiftshift.presentation.features.nearby_gigs.NearbyGigsScreen
import com.daffa.swiftshift.presentation.features.onboarding.OnBoardingScreen
import com.daffa.swiftshift.presentation.features.profile.ProfileScreen
import com.daffa.swiftshift.presentation.features.register.RegisterScreen
import com.daffa.swiftshift.presentation.features.search.SearchScreen
import com.daffa.swiftshift.presentation.features.splash.SplashScreen
import com.daffa.swiftshift.presentation.util.NavArguments
import com.daffa.swiftshift.util.Constants.Empty

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
            SearchScreen(navController = navController, scaffoldState = scaffoldState)
        }
        composable(Screen.HistoryScreen.route) {
            HistoryScreen(navController = navController)
        }
        composable(
            "${Screen.NearbyGigsScreen.route}/{${NavArguments.NAV_ARG_LATITUDE}}/{${NavArguments.NAV_ARG_LONGITUDE}}",
            arguments = listOf(
                navArgument(NavArguments.NAV_ARG_LATITUDE) {
                    type = NavType.FloatType
                    defaultValue = 0.0
                },
                navArgument(NavArguments.NAV_ARG_LONGITUDE) {
                    type = NavType.FloatType
                    defaultValue = 0.0
                }
            )
        ) {
            val latitude = it.arguments?.getFloat(NavArguments.NAV_ARG_LATITUDE) ?: 0.0f
            val longitude = it.arguments?.getFloat(NavArguments.NAV_ARG_LONGITUDE) ?: 0.0f
            NearbyGigsScreen(
                navController = navController,
                latitude = latitude.toDouble(),
                longitude = longitude.toDouble(),
                scaffoldState = scaffoldState
            )
        }
        composable(
            Screen.RecommendedGigsScreen.route,
            arguments = listOf(
                navArgument(NavArguments.NAV_ARG_LATITUDE) {
                    type = NavType.FloatType
                    defaultValue = 0.0
                },
                navArgument(NavArguments.NAV_ARG_LONGITUDE) {
                    type = androidx.navigation.NavType.FloatType
                    defaultValue = 0.0
                }
            )
        ) {

        }
        composable(
            "{${Screen.NearbyGigsScreen.route}}/{${NavArguments.NAV_GIG_ID}}",
            arguments = listOf(
                navArgument(NavArguments.NAV_GIG_ID) {
                    type = NavType.StringType
                    defaultValue = String.Empty
                }
            )
        ) {
            val gigId = it.arguments?.getString(NavArguments.NAV_GIG_ID) ?: String.Empty
            GigDetailScreen(
                navController = navController,
                gigId = gigId,
                scaffoldState = scaffoldState
            )
        }
    }
}