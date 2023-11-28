package com.daffa.swiftshift.presentation.features.register.page

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
import com.daffa.swiftshift.presentation.ui.theme.SpaceSmall
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.daffa.swiftshift.util.Constants.Empty
import com.daffa.swiftshift.util.error.AuthError
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterThirdPage(
    pagerState: PagerState,
    viewModel: RegisterViewModel,
) {
    val fullNameState by viewModel.fullNameState
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
                text = stringResource(R.string.what_is_your_name),
                style = Type.heading4Bold()
            )
            Spacer(modifier = Modifier.height(SpaceSmall))
            Text(
                text = stringResource(R.string.second_register_description),
                style = Type.body4Regular()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            verticalArrangement = Arrangement.Top
        ) {
            SwiftShiftTextField(
                text = fullNameState.text,
                onValueChange = {
                    viewModel.onEvent(RegisterEvent.EnterFullName(it))
                },
                error = when (fullNameState.error) {
                    is AuthError.FieldEmpty -> {
                        stringResource(R.string.this_field_cannot_be_empty)
                    }

                    else -> String.Empty
                },
                label = stringResource(R.string.full_name),
                hint = stringResource(R.string.hint_full_name)
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
                    if (fullNameState.error == null)
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