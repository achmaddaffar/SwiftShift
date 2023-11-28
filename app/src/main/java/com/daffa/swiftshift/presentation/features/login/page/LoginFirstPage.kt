package com.daffa.swiftshift.presentation.features.login.page

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.features.login.LoginEvent
import com.daffa.swiftshift.presentation.features.login.LoginViewModel
import com.daffa.swiftshift.presentation.features.login.component.RoleSelectionList
import com.daffa.swiftshift.presentation.features.register.RegisterEvent
import com.daffa.swiftshift.presentation.ui.theme.Primary600
import com.daffa.swiftshift.presentation.ui.theme.Slate300
import com.daffa.swiftshift.presentation.ui.theme.SpaceLarge
import com.daffa.swiftshift.presentation.ui.theme.Type
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginFirstPage(
    pagerState: PagerState,
    viewModel: LoginViewModel
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(SpaceLarge)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .padding(bottom = SpaceLarge),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.you_want_to_login_as),
                style = Type.heading4Bold()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            verticalArrangement = Arrangement.Top
        ) {
            // Content
            RoleSelectionList(
                options = viewModel.options,
                onOptionClicked = {
                    viewModel.onEvent(LoginEvent.SelectRole(it))
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                },
                enabled = viewModel.isRoleSelected(),
                colors = ButtonDefaults.buttonColors(disabledContainerColor = Slate300),
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(
                    text = stringResource(R.string.next),
                    style = Type.heading4SemiBold(),
                    color = Color.White
                )
            }
        }
    }
}