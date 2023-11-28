package com.daffa.swiftshift.presentation.features.login

import android.content.pm.ActivityInfo
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Icon
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.component.SwiftShiftTextField
import com.daffa.swiftshift.presentation.features.login.page.LoginFirstPage
import com.daffa.swiftshift.presentation.features.login.page.LoginSecondPage
import com.daffa.swiftshift.presentation.navigation.Screen
import com.daffa.swiftshift.presentation.ui.theme.IconSizeMedium
import com.daffa.swiftshift.presentation.ui.theme.Primary500
import com.daffa.swiftshift.presentation.ui.theme.Primary600
import com.daffa.swiftshift.presentation.ui.theme.SpaceLarge
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.ui.theme.SpaceSmall
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.daffa.swiftshift.presentation.util.LockScreenOrientation
import com.daffa.swiftshift.util.Constants
import com.daffa.swiftshift.util.Constants.Empty
import com.daffa.swiftshift.util.error.AuthError
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val pagerState = rememberPagerState { 2 }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(SpaceSmall)
                .padding(start = SpaceMedium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(
                onClick = {
                    if (pagerState.currentPage == 0)
                        navController.popBackStack()
                    else
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                },
                modifier = Modifier.size(IconSizeMedium)
            ) {
                androidx.compose.material3.Icon(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = stringResource(R.string.back_button),
                    tint = Color.Black,
                )
            }
            Spacer(modifier = Modifier.width(SpaceMedium))
            Text(
                text = stringResource(id = R.string.login),
                style = Type.heading5Bold(),
                color = Color.Black
            )
        }
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) { index ->
            when(index) {
                0 -> LoginFirstPage(pagerState = pagerState, viewModel = viewModel)
                1 -> LoginSecondPage(
                    viewModel = viewModel,
                    scaffoldState = scaffoldState,
                    navController = navController
                )
            }
        }
    }
}