package com.daffa.swiftshift.presentation.features.onboarding

import android.content.pm.ActivityInfo
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.daffa.swiftshift.presentation.navigation.Screen
import com.daffa.swiftshift.presentation.ui.theme.SpaceLarge
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.ui.theme.SpaceSmall
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.daffa.swiftshift.presentation.util.LockScreenOrientation
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    navController: NavController,
) {
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    val pages = listOf(
        OnBoardingPage.FirstPage,
        OnBoardingPage.SecondPage,
        OnBoardingPage.ThirdPage
    )

    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })

    OnBoardingPager(
        item = pages,
        pagerState = pagerState,
        navController = navController
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingPager(
    item: List<OnBoardingPage>,
    pagerState: PagerState,
    navController: NavController,
) {
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth()
            ) { index ->
                Image(
                    painter = painterResource(id = item[index].imageId),
                    contentDescription = stringResource(item[index].imageContentDesc)
                )

                Spacer(modifier = Modifier.height(SpaceMedium))
                PagerIndicator(item = item, currentPage = pagerState.currentPage)
                Spacer(modifier = Modifier.height(SpaceMedium))

                Text(
                    text = stringResource(item[index].titleId),
                    style = Type.heading3SemiBold()
                )
                Spacer(modifier = Modifier.height(SpaceSmall))
                Text(
                    text = stringResource(id = item[index].descriptionId),
                    style = Type.body3Light(),
                    textAlign = TextAlign.Center
                )
            }
        }
        Button(
            onClick = {
                if (pagerState.currentPage < item.size - 1)
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                else
                    navController.navigate(Screen.LoginScreen.route)
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = SpaceLarge)
        ) {
            Text(
                text = "Continue",
                style = Type.heading3Medium()
            )
        }
    }
}