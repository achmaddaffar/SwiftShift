package com.daffa.swiftshift.presentation.features.register.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.features.register.RegisterViewModel
import com.daffa.swiftshift.presentation.navigation.Screen
import com.daffa.swiftshift.presentation.ui.theme.Primary600
import com.daffa.swiftshift.presentation.ui.theme.Primary700
import com.daffa.swiftshift.presentation.ui.theme.SpaceLarge
import com.daffa.swiftshift.presentation.ui.theme.SpaceSmall
import com.daffa.swiftshift.presentation.ui.theme.Type

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterFourthPage(
    navController: NavController,
    pagerState: PagerState,
    viewModel: RegisterViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(SpaceLarge)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.register_step, pagerState.currentPage + 1),
                style = Type.body2Bold(),
                color = Primary600
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.upload_your_photo),
                style = Type.heading4Bold()
            )
            Spacer(modifier = Modifier.height(SpaceSmall))
            Text(
                text = stringResource(R.string.fourth_register_description),
                style = Type.body4Regular()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .size(125.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.btn_gallery),
                    contentDescription = stringResource(R.string.gallery)
                )
            }
            Spacer(modifier = Modifier.height(SpaceSmall))
            Text(
                text = stringResource(R.string.tap_here_to_upload),
                style = Type.body5Regular()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = {
                    navController.navigate(Screen.LoginScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                border = BorderStroke(2.dp, Primary700)
            ) {
                Text(
                    text = stringResource(R.string.i_will_do_it_later),
                    style = Type.heading4SemiBold(),
                    color = Primary700
                )
            }
        }
    }
}