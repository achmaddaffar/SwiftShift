package com.daffa.swiftshift.presentation.features.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.daffa.swiftshift.domain.util.ValidationUtil
import com.daffa.swiftshift.presentation.util.state.BaseTextFieldState
import com.daffa.swiftshift.presentation.util.state.PasswordTextFieldState
import com.daffa.swiftshift.presentation.util.state.SelectionOption
import com.daffa.swiftshift.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(

) : ViewModel() {

    private val _emailState = mutableStateOf(BaseTextFieldState())
    val emailState: State<BaseTextFieldState> = _emailState

    private val _fullNameState = mutableStateOf(BaseTextFieldState())
    val fullNameState: State<BaseTextFieldState> = _fullNameState

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> = _passwordState

    private val _confirmPasswordState = mutableStateOf(PasswordTextFieldState())
    val confirmPasswordState: State<PasswordTextFieldState> = _confirmPasswordState

    private val _options = listOf(
        SelectionOption(Constants.GIG_WORKER, false),
        SelectionOption(Constants.GIG_PROVIDER, false)
    ).toMutableStateList()
    val options: List<SelectionOption<String>>
        get() = _options

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EnteredEmailAddress -> {
                _emailState.value = _emailState.value.copy(
                    text = event.value
                )
            }

            is RegisterEvent.EnteredFullName -> {
                _fullNameState.value = _fullNameState.value.copy(
                    text = event.value
                )
            }

            is RegisterEvent.EnteredPassword -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.value
                )
            }

            is RegisterEvent.EnteredConfirmPassword -> {
                _confirmPasswordState.value = _confirmPasswordState.value.copy(
                    text = event.value
                )
            }

            is RegisterEvent.ProceedNextScreen -> {
                proceedNextScreen(event.destinationIndex)
            }


            RegisterEvent.InsertProfilePicture -> {

            }

            RegisterEvent.Register -> {

            }


            RegisterEvent.RegisterWithoutProfilePicture -> {

            }

            RegisterEvent.TogglePasswordVisibility -> {
                _passwordState.value = passwordState.value.copy(
                    isPasswordVisible = !passwordState.value.isPasswordVisible
                )
            }

            RegisterEvent.ToggleConfirmPasswordVisibility -> {
                _confirmPasswordState.value = confirmPasswordState.value.copy(
                    isPasswordVisible = !confirmPasswordState.value.isPasswordVisible
                )
            }

            is RegisterEvent.SelectedRole -> {
                _options.forEach { it.selected = false }
                _options.find { it.option == event.selectionOption.option }?.selected = true
            }
        }
    }

    fun isRoleSelected(): Boolean {
        _options.forEach {
            if (it.selected)
                return true
        }
        return false
    }

    private fun proceedNextScreen(destinationIndex: Int) {
        when (destinationIndex) {
            2 -> {
                _emailState.value = emailState.value.copy(error = null)

                val emailError = ValidationUtil.validateEmail(emailState.value.text)

                if (emailError != null) {
                    _emailState.value = emailState.value.copy(
                        error = emailError
                    )
                }
            }

            3 -> {
                _fullNameState.value = fullNameState.value.copy(error = null)

                val fullNameError = ValidationUtil.validateFullName(fullNameState.value.text)

                if (fullNameError != null) {
                    _fullNameState.value = fullNameState.value.copy(
                        error = fullNameError
                    )
                }
            }

            4 -> {
                _passwordState.value = passwordState.value.copy(error = null)
                _confirmPasswordState.value = confirmPasswordState.value.copy(error = null)

                val passwordError = ValidationUtil.validatePassword(passwordState.value.text)
                val confirmPasswordError =
                    ValidationUtil.validatePassword(confirmPasswordState.value.text)
                if (passwordError != null) {
                    _passwordState.value = passwordState.value.copy(
                        error = passwordError
                    )
                }

                if (confirmPasswordError != null) {
                    _confirmPasswordState.value = confirmPasswordState.value.copy(
                        error = confirmPasswordError
                    )
                }

                if (passwordError == null && confirmPasswordError == null) {
                    val matchPasswordError = ValidationUtil.validateConfirmPassword(
                        passwordState.value.text,
                        confirmPasswordState.value.text
                    )

                    if (matchPasswordError != null) {
                        _passwordState.value = passwordState.value.copy(
                            error = matchPasswordError
                        )
                        _confirmPasswordState.value = confirmPasswordState.value.copy(
                            error = matchPasswordError
                        )
                    }
                }
            }
        }
    }
}