package com.daffa.swiftshift.presentation.features.register.component

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.component.SwiftShiftTextField
import com.daffa.swiftshift.presentation.features.register.RegisterEvent
import com.daffa.swiftshift.presentation.features.register.RegisterViewModel
import com.daffa.swiftshift.presentation.ui.theme.Primary600
import com.daffa.swiftshift.presentation.ui.theme.SpaceLarge
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.daffa.swiftshift.util.Constants
import com.daffa.swiftshift.util.Constants.Empty
import com.daffa.swiftshift.util.error.AuthError
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterThirdPage(
    pagerState: PagerState,
    viewModel: RegisterViewModel,
) {
    val passwordState by viewModel.passwordState
    val confirmPasswordState by viewModel.confirmPasswordState
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
                text = stringResource(R.string.create_a_new_password),
                style = Type.heading4Bold()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            verticalArrangement = Arrangement.Top
        ) {
            SwiftShiftTextField(
                text = passwordState.text,
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.EnteredPassword(it))
                },
                error = when (passwordState.error) {
                    AuthError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cannot_be_empty)
                    }

                    AuthError.InvalidPassword -> {
                        stringResource(R.string.invalid_password)
                    }

                    AuthError.InputTooShort -> {
                        stringResource(id = R.string.input_too_short, Constants.MIN_PASSWORD_LENGTH)
                    }

                    AuthError.PasswordDoesNotMatch -> {
                        stringResource(R.string.error_match_password)
                    }

                    else -> String.Empty
                },
                hint = stringResource(R.string.enter_your_password),
                label = stringResource(R.string.enter_your_new_password),
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
            SwiftShiftTextField(
                text = confirmPasswordState.text,
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.EnteredConfirmPassword(it))
                },
                error = when (passwordState.error) {
                    AuthError.FieldEmpty -> {
                        stringResource(id = R.string.this_field_cannot_be_empty)
                    }

                    AuthError.InvalidPassword -> {
                        stringResource(R.string.invalid_password)
                    }

                    AuthError.InputTooShort -> {
                        stringResource(id = R.string.input_too_short, Constants.MIN_PASSWORD_LENGTH)
                    }

                    AuthError.PasswordDoesNotMatch -> {
                        stringResource(R.string.error_match_password)
                    }

                    else -> String.Empty
                },
                hint = stringResource(R.string.confirm_your_password),
                label = stringResource(R.string.confirm_your_new_password),
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
                    viewModel.onEvent(RegisterEvent.ProceedNextScreen(pagerState.currentPage + 1))
                    if (passwordState.error == null && confirmPasswordState.error == null)
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                },
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