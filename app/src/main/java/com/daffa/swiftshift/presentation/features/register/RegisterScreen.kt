package com.daffa.swiftshift.presentation.features.register

import android.content.pm.ActivityInfo
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.features.register.page.RegisterSecondPage
import com.daffa.swiftshift.presentation.features.register.page.RegisterFifthPage
import com.daffa.swiftshift.presentation.features.register.page.RegisterFirstScreen
import com.daffa.swiftshift.presentation.features.register.page.RegisterThirdPage
import com.daffa.swiftshift.presentation.features.register.page.RegisterFourthPage
import com.daffa.swiftshift.presentation.ui.theme.IconSizeMedium
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.ui.theme.SpaceSmall
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.daffa.swiftshift.presentation.util.LockScreenOrientation
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val pagerState = rememberPagerState { 5 }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                },
                modifier = Modifier.size(IconSizeMedium)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = stringResource(R.string.back_button),
                    tint = Color.Black,
                )
            }
            Spacer(modifier = Modifier.width(SpaceMedium))
            Text(
                text = stringResource(R.string.create_an_account),
                style = Type.heading5Bold(),
                color = Color.Black
            )
        }
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false
        ) { index ->
            when (index) {
                0 -> RegisterFirstScreen(pagerState = pagerState, viewModel = viewModel)
                1 -> RegisterSecondPage(pagerState = pagerState, viewModel = viewModel)
                2 -> RegisterThirdPage(pagerState = pagerState, viewModel = viewModel)
                3 -> RegisterFourthPage(pagerState = pagerState, viewModel = viewModel)
                4 -> RegisterFifthPage(
                    navController = navController,
                    pagerState = pagerState,
                    viewModel = viewModel
                )
            }
        }
    }
}