package com.daffa.swiftshift.presentation.features.register

import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daffa.swiftshift.domain.use_case.RegisterGigProviderUseCase
import com.daffa.swiftshift.domain.use_case.RegisterGigWorkerUseCase
import com.daffa.swiftshift.domain.util.ValidationUtil
import com.daffa.swiftshift.presentation.util.state.BaseTextFieldState
import com.daffa.swiftshift.presentation.util.state.PasswordTextFieldState
import com.daffa.swiftshift.presentation.util.state.SelectionOption
import com.daffa.swiftshift.util.Constants
import com.daffa.swiftshift.util.Resource
import com.daffa.swiftshift.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerGigWorker: RegisterGigWorkerUseCase,
    private val registerGigProvider: RegisterGigProviderUseCase
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

    private val _registerState = mutableStateOf(RegisterState())
    val registerState: State<RegisterState> = _registerState

    private val channel = Channel<UiEvent>()
    val uiChannelFlow = channel.receiveAsFlow()

    private val _chosenImageUri = mutableStateOf<Uri?>(null)
    val chosenImageUri: State<Uri?> = _chosenImageUri

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


            is RegisterEvent.PickImage -> {
                _chosenImageUri.value = event.uri
            }

            is RegisterEvent.CropImage -> {
                _chosenImageUri.value = event.uri
            }

            RegisterEvent.RemovePhoto -> {
                _chosenImageUri.value = null
            }

            RegisterEvent.Register -> {
                when (selectedRole()) {
                    Constants.GIG_WORKER -> {
                        viewModelScope.launch {
                            registerGigWorker(
                                fullName = fullNameState.value.text,
                                email = emailState.value.text,
                                password = passwordState.value.text,
                                imageUri = chosenImageUri.value
                            ).collect { result ->
                                when (result) {
                                    is Resource.Error -> {
                                        _registerState.value = _registerState.value.copy(
                                            isLoading = false
                                        )
                                        result.uiText?.let {
                                            channel.send(
                                                UiEvent.ShowSnackBar(it)
                                            )
                                        }
                                    }

                                    is Resource.Loading -> {
                                        _registerState.value = _registerState.value.copy(
                                            isLoading = true
                                        )
                                    }

                                    is Resource.Success -> {
                                        _registerState.value = _registerState.value.copy(
                                            isLoading = false
                                        )
                                        channel.send(UiEvent.NavigateToLogin)
                                    }
                                }
                            }
                        }
                    }

                    Constants.GIG_PROVIDER -> {
                        viewModelScope.launch {
                            registerGigProvider(
                                fullName = fullNameState.value.text,
                                email = emailState.value.text,
                                password = passwordState.value.text,
                                imageUri = chosenImageUri.value
                            ).collect { result ->
                                when (result) {
                                    is Resource.Error -> {
                                        _registerState.value = _registerState.value.copy(
                                            isLoading = false
                                        )
                                        result.uiText?.let {
                                            channel.send(
                                                UiEvent.ShowSnackBar(it)
                                            )
                                        }
                                    }

                                    is Resource.Loading -> {
                                        _registerState.value = _registerState.value.copy(
                                            isLoading = true
                                        )
                                    }

                                    is Resource.Success -> {
                                        _registerState.value = _registerState.value.copy(
                                            isLoading = true
                                        )
                                        channel.send(UiEvent.NavigateToLogin)
                                    }
                                }
                            }
                        }
                    }
                }
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

    private fun selectedRole(): String {
        return _options.first { it.selected }.option
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

    sealed class UiEvent {
        data class ShowSnackBar(val uiText: UiText) : UiEvent()
        data object NavigateToLogin : UiEvent()
    }
}