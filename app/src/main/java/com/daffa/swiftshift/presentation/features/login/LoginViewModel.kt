package com.daffa.swiftshift.presentation.features.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.daffa.swiftshift.presentation.util.state.BaseTextFieldState
import com.daffa.swiftshift.presentation.util.state.PasswordTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

) : ViewModel() {

    private val _emailState = mutableStateOf(BaseTextFieldState())
    val emailState: State<BaseTextFieldState> = _emailState

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> = _passwordState

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _emailState.value = _emailState.value.copy(
                    text = event.value
                )
            }

            is LoginEvent.EnteredPassword -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.value
                )
            }

            LoginEvent.TogglePasswordVisibility -> {
                _passwordState.value = _passwordState.value.copy(
                    isPasswordVisible = !_passwordState.value.isPasswordVisible
                )
            }

            LoginEvent.Login -> {

            }
        }
    }
}