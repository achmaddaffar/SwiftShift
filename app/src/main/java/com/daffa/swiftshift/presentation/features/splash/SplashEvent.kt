package com.daffa.swiftshift.presentation.features.splash

sealed class SplashEvent {
    data object NavigateToOnBoarding : SplashEvent()
    data object NavigateToLogin : SplashEvent()
}
