package com.daffa.swiftshift.presentation.features.onboarding

import android.content.pm.ActivityInfo
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.navigation.Screen
import com.daffa.swiftshift.presentation.ui.theme.Primary500
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            verticalAlignment = Alignment.Bottom
        ) { index ->
            Image(
                painter = painterResource(id = item[index].imageId),
                contentDescription = stringResource(item[index].imageContentDesc),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .background(Color.Red)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.height(SpaceMedium))
            PagerIndicator(item = item, currentPage = pagerState.currentPage)
            Spacer(modifier = Modifier.height(SpaceLarge))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(item[pagerState.currentPage].titleId),
                style = Type.heading3SemiBold(),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = SpaceLarge * 2)
            )
            Spacer(modifier = Modifier.height(SpaceSmall))
            Text(
                text = stringResource(id = item[pagerState.currentPage].descriptionId),
                style = Type.body3Light(),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = SpaceLarge * 2)
            )
        }
        Column(
            modifier = Modifier
                .padding(bottom = SpaceLarge)
                .fillMaxWidth()
                .fillMaxHeight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = {
                    if (pagerState.currentPage < item.size - 1)
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    else
                        navController.navigate(Screen.RegisterScreen.route)
                },
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text(
                    text = stringResource(
                        if (pagerState.currentPage < item.size - 1) R.string.btn_continue else
                            R.string.sign_me_now
                    ),
                    style = Type.body3Medium(),
                    color = Color.White
                )
            }
            AnimatedVisibility(
                visible = pagerState.currentPage == item.size - 1
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = SpaceSmall),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.already_have_an_account),
                        style = Type.body4Regular(),
                        color = Color.Black
                    )
                    Text(
                        text = "Login",
                        style = Type.body4Regular(),
                        color = Primary500,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.LoginScreen.route)
                        }
                    )
                }
            }
        }
    }
}