package com.daffa.swiftshift.presentation.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import com.daffa.swiftshift.presentation.navigation.component.ModalNavigationDrawerContent
import com.daffa.swiftshift.presentation.navigation.component.PermanentNavigationDrawerContent
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftContentType
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftNavigationContentPosition
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftNavigationType
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwiftShiftNavigationWrapper(
    navigationType: SwiftShiftNavigationType,
    contentType: SwiftShiftContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: SwiftShiftNavigationContentPosition,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        SwiftShiftNavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: Screen.HomeScreen.route

    if (navigationType == SwiftShiftNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentNavigationDrawerContent(
                    selectedDestination = selectedDestination,
                    navigationContentPosition = navigationContentPosition,
                    navigateToTopLevelDestination = navigationActions::navigateTo
                )
            }
        ) {
            SwiftShiftAppContent(
                navigationType = navigationType,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                navController = navController,
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigationActions::navigateTo
            )
        }
    } else {
        ModalNavigationDrawer(
            drawerContent = {
                ModalNavigationDrawerContent(
                    selectedDestination = selectedDestination,
                    navigationContentPosition = navigationContentPosition,
                    navigateToTopLevelDestination = navigationActions::navigateTo,
                    onDrawerClicked = {
                        scope.launch {
                            if (drawerState.isClosed)
                                drawerState.open()
                            else
                                drawerState.close()
                        }
                    }
                )
            },
            drawerState = drawerState
        ) {
            SwiftShiftAppContent(
                navigationType = navigationType,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                navController = navController,
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigationActions::navigateTo
            ) {
                scope.launch {
                    drawerState.open()
                }
            }
        }
    }
}