package com.daffa.swiftshift.presentation.navigation

sealed class Screen(val route: String) {
    data object SplashScreen: Screen("splash_screen")
    data object OnBoardingScreen: Screen("on_boarding_screen")
    data object LoginScreen: Screen("login_screen")
    data object RegisterScreen: Screen("register_screen")
    data object HomeScreen: Screen("home_screen")
    data object SearchScreen: Screen("search_screen")
    data object HistoryScreen: Screen("history_screen")
    data object ProfileScreen: Screen("profile_screen")

    fun withArgs(vararg args: String) = buildString {
        append(route)
        args.forEach { arg ->
            append("/$arg")
        }
    }
}