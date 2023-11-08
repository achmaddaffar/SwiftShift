package com.daffa.swiftshift.presentation.navigation

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object OnBoardingScreen: Screen("on_boarding_screen")
    object LoginScreen: Screen("login_screen")
    object RegisterScreen: Screen("register_screen")
    object HomeScreen: Screen("home_screen")
    object SearchScreen: Screen("search_screen")
    object HistoryScreen: Screen("history_screen")
    object ProfileScreen: Screen("profile_screen")

    fun withArgs(vararg args: String) = buildString {
        append(route)
        args.forEach { arg ->
            append("/$arg")
        }
    }
}