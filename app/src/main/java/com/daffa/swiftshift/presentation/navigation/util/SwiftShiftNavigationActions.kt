package com.daffa.swiftshift.presentation.navigation.util

import androidx.navigation.NavController
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.navigation.Screen

class SwiftShiftNavigationActions(private val navController: NavController) {

    fun navigateTo(destination: SwiftShiftTopLevelDestination) {
        navController.navigate(destination.route) {
            popUpTo(navController.graph.id) {
                saveState = true
                inclusive = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun getNavController() = navController
}

val TOP_LEVEL_DESTINATION = listOf(
    SwiftShiftTopLevelDestination(
        route = Screen.HomeScreen.route,
        unselectedIconId = R.drawable.icon_home_unselected,
        selectedIconId = R.drawable.icon_home_selected,
        iconTextId = R.string.home,
        labelId = R.string.home
    ),
    SwiftShiftTopLevelDestination(
        route = Screen.SearchScreen.route,
        unselectedIconId = R.drawable.icon_search_unselected,
        selectedIconId = R.drawable.icon_search_selected,
        iconTextId = R.string.search,
        labelId = R.string.search
    ),
    SwiftShiftTopLevelDestination(
        route = Screen.HistoryScreen.route,
        unselectedIconId = R.drawable.icon_history_unselected,
        selectedIconId = R.drawable.icon_history_selected,
        iconTextId = R.string.history,
        labelId = R.string.history
    ),
    SwiftShiftTopLevelDestination(
        route = Screen.ProfileScreen.route,
        unselectedIconId = R.drawable.icon_profile_unselected,
        selectedIconId = R.drawable.icon_profile_selected,
        iconTextId = R.string.profile,
        labelId = R.string.profile
    )
)