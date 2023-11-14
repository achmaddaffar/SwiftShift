package com.daffa.swiftshift.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.daffa.swiftshift.presentation.navigation.Navigation
import com.daffa.swiftshift.presentation.navigation.component.SwiftShiftScaffold
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftNavigationActions
import com.daffa.swiftshift.presentation.navigation.util.TOP_LEVEL_DESTINATION
import com.daffa.swiftshift.presentation.ui.theme.SwiftShiftTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwiftShiftTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val navigationActions = remember(navController) {
                        SwiftShiftNavigationActions(navController)
                    }
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val scaffoldState = rememberScaffoldState()
//                    val selectedDestination =
//                        navBackStackEntry?.destination?.route ?: Screen.SplashScreen.route

                    SwiftShiftScaffold(
                        navigationActions = navigationActions,
                        state = scaffoldState,
                        showBottomBar = navBackStackEntry?.destination?.route in TOP_LEVEL_DESTINATION.map { it.route },
                    ) {
                        Navigation(
                            navController = navController,
                            scaffoldState = scaffoldState
                        )
                    }
                }
            }
        }
    }
}