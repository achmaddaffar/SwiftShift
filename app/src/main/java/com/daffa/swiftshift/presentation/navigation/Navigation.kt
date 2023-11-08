package com.daffa.swiftshift.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.window.layout.DisplayFeature
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftContentType
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftNavigationType

@Composable
fun Navigation(
    navController: NavHostController,
    contentType: SwiftShiftContentType,
    displayFeatures: List<DisplayFeature>,
    navigationType: SwiftShiftNavigationType,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        modifier = modifier
    ) {
        composable(Screen.SplashScreen.route) {

        }
    }
}