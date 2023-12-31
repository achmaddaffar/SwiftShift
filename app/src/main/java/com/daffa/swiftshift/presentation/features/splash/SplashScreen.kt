package com.daffa.swiftshift.presentation.features.splash

import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.navigation.Screen
import com.daffa.swiftshift.presentation.ui.theme.Primary800
import com.daffa.swiftshift.presentation.util.LockScreenOrientation
import com.daffa.swiftshift.presentation.util.ObserveAsEvents
import com.daffa.swiftshift.util.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val alpha = remember {
        Animatable(0f)
    }

    ObserveAsEvents(flow = viewModel.eventFlow) { event ->
        when(event) {
            SplashViewModel.UiEvent.NavigateToHome -> {
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(Screen.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }
            SplashViewModel.UiEvent.NavigateToOnBoarding -> {
                navController.navigate(Screen.OnBoardingScreen.route) {
                    popUpTo(Screen.SplashScreen.route) {
                        inclusive = true
                    }
                }
            }

            is SplashViewModel.UiEvent.ShowToast -> {
                Toast.makeText(
                    context,
                    event.uiText.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    LaunchedEffect(key1 = true) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000
            )
        )
        delay(Constants.SPLASH_SCREEN_DURATION)
        viewModel.auth()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary800)
            .alpha(alpha.value),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = stringResource(id = R.string.app_logo),
            modifier = Modifier.width(screenWidth / 2)
        )
    }
}