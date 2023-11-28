package com.daffa.swiftshift.presentation.features.login.page

import android.widget.Toast
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
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.wear.compose.material.Icon
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.component.SwiftShiftTextField
import com.daffa.swiftshift.presentation.features.login.LoginEvent
import com.daffa.swiftshift.presentation.features.login.LoginViewModel
import com.daffa.swiftshift.presentation.navigation.Screen
import com.daffa.swiftshift.presentation.ui.theme.IconSizeMedium
import com.daffa.swiftshift.presentation.ui.theme.Primary500
import com.daffa.swiftshift.presentation.ui.theme.Primary600
import com.daffa.swiftshift.presentation.ui.theme.SpaceLarge
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.ui.theme.SpaceSmall
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.daffa.swiftshift.presentation.util.ObserveAsEvents
import com.daffa.swiftshift.util.Constants
import com.daffa.swiftshift.util.Constants.Empty
import com.daffa.swiftshift.util.asString
import com.daffa.swiftshift.util.error.AuthError
import kotlinx.coroutines.launch

@Composable
fun LoginSecondPage(
    viewModel: LoginViewModel,
    scaffoldState: ScaffoldState,
    navController: NavController,
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val emailState by viewModel.emailState
    val passwordState by viewModel.passwordState
    val isLoading by viewModel.isLoading

    ObserveAsEvents(flow = viewModel.eventFlow) { event ->
        when (event) {
            LoginViewModel.UiEvent.NavigateToHomeScreen -> {
                Toast.makeText(
                    context,
                    R.string.success_login,
                    Toast.LENGTH_LONG
                ).show()
                navController.navigate(Screen.HomeScreen.route) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }

            is LoginViewModel.UiEvent.ShowSnackbar -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context),
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

    if (isLoading)
        Dialog(
            onDismissRequest = {}
        ) {
            CircularProgressIndicator(
                color = Primary600,
                modifier = Modifier.size(100.dp)
            )
        }

    Column(
        modifier = Modifier.fillMaxSize()
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
                    text = stringResource(R.string.welcome_back),
                    style = Type.heading4Bold()
                )
                Spacer(modifier = Modifier.height(SpaceSmall))
                Text(
                    text = stringResource(R.string.login_description),
                    style = Type.body4Regular()
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.8f),
                verticalArrangement = Arrangement.Top
            ) {
                // content
                SwiftShiftTextField(
                    text = emailState.text,
                    onValueChange = {
                        viewModel.onEvent(LoginEvent.EnterEmail(it))
                    },
                    error = when (emailState.error) {
                        is AuthError.FieldEmpty -> {
                            stringResource(R.string.this_field_cannot_be_empty)
                        }

                        is AuthError.InvalidEmail -> {
                            stringResource(R.string.error_invalid_email)
                        }

                        else -> String.Empty
                    },
                    label = stringResource(R.string.email_address),
                    hint = stringResource(R.string.hint_email_address),
                )
                Spacer(modifier = Modifier.height(SpaceMedium))
                SwiftShiftTextField(
                    text = passwordState.text,
                    onValueChange = {
                        viewModel.onEvent(LoginEvent.EnterPassword(it))
                    },
                    error = when (passwordState.error) {
                        AuthError.FieldEmpty -> {
                            stringResource(id = R.string.this_field_cannot_be_empty)
                        }

                        AuthError.InvalidPassword -> {
                            stringResource(R.string.invalid_password)
                        }

                        AuthError.InputTooShort -> {
                            stringResource(
                                id = R.string.input_too_short,
                                Constants.MIN_PASSWORD_LENGTH
                            )
                        }

                        AuthError.PasswordDoesNotMatch -> {
                            stringResource(R.string.error_match_password)
                        }

                        else -> String.Empty
                    },
                    hint = stringResource(R.string.enter_your_password),
                    label = stringResource(R.string.password),
                    keyboardType = KeyboardType.Password,
                    showPasswordToggle = passwordState.isPasswordVisible,
                    onPasswordToggleClick = {
                        viewModel.onEvent(LoginEvent.TogglePasswordVisibility)
                    }
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(bottom = SpaceLarge),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = {
                        viewModel.onEvent(LoginEvent.Login)
                    },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text(
                        text = stringResource(R.string.login),
                        style = Type.heading4SemiBold(),
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(SpaceSmall))
                Row {
                    Text(
                        text = stringResource(R.string.don_t_have_any_account),
                        style = Type.body5Regular()
                    )
                    Text(
                        text = stringResource(R.string.register),
                        style = Type.body5Regular(),
                        color = Primary500,
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.RegisterScreen.route)
                        }
                    )
                }
            }
        }
    }
}