package com.daffa.swiftshift.presentation.features.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daffa.swiftshift.domain.use_case.gig_provider.LoginGigProviderUseCase
import com.daffa.swiftshift.domain.use_case.gig_worker.LoginGigWorkerUseCase
import com.daffa.swiftshift.presentation.util.state.BaseTextFieldState
import com.daffa.swiftshift.presentation.util.state.PasswordTextFieldState
import com.daffa.swiftshift.presentation.util.state.SelectionOption
import com.daffa.swiftshift.util.Constants
import com.daffa.swiftshift.util.Resource
import com.daffa.swiftshift.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginGigWorker: LoginGigWorkerUseCase,
    private val loginGigProvider: LoginGigProviderUseCase,
) : ViewModel() {

    private val _emailState = mutableStateOf(BaseTextFieldState())
    val emailState: State<BaseTextFieldState> = _emailState

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> = _passwordState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _options = listOf(
        SelectionOption(Constants.GIG_WORKER, false),
        SelectionOption(Constants.GIG_PROVIDER, false)
    ).toMutableStateList()
    val options: List<SelectionOption<String>>
        get() = _options

    private val channel = Channel<UiEvent>()
    val eventFlow = channel.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnterEmail -> {
                _emailState.value = _emailState.value.copy(
                    text = event.value
                )
            }

            is LoginEvent.EnterPassword -> {
                _passwordState.value = _passwordState.value.copy(
                    text = event.value
                )
            }

            LoginEvent.TogglePasswordVisibility -> {
                _passwordState.value = _passwordState.value.copy(
                    isPasswordVisible = !_passwordState.value.isPasswordVisible
                )
            }

            is LoginEvent.SelectRole -> {
                _options.forEach { it.selected = false }
                _options.find { it.option == event.selectionOption.option }?.selected = true
            }

            LoginEvent.Login -> {
                viewModelScope.launch {
                    when (selectedRole()) {
                        Constants.GIG_WORKER -> {
                            loginGigWorker(
                                email = emailState.value.text,
                                password = passwordState.value.text
                            ).collect { result ->
                                when (result) {
                                    is Resource.Error -> {
                                        _isLoading.value = false
                                        result.uiText?.let {
                                            channel.send(
                                                UiEvent.ShowSnackbar(it)
                                            )
                                        }
                                    }

                                    is Resource.Loading -> {
                                        _isLoading.value = true
                                    }

                                    is Resource.Success -> {
                                        _isLoading.value = false
                                        channel.send(UiEvent.NavigateToHomeScreen)
                                    }
                                }
                            }
                        }

                        Constants.GIG_PROVIDER -> {
                            loginGigProvider(
                                email = emailState.value.text,
                                password = passwordState.value.text
                            ).collect { result ->
                                when (result) {
                                    is Resource.Error -> {
                                        _isLoading.value = false
                                        result.uiText?.let {
                                            channel.send(
                                                UiEvent.ShowSnackbar(it)
                                            )
                                        }
                                    }

                                    is Resource.Loading -> {
                                        _isLoading.value = true
                                    }

                                    is Resource.Success -> {
                                        _isLoading.value = false
                                        channel.send(UiEvent.NavigateToHomeScreen)
                                    }
                                }
                            }
                        }
                    }
                }
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

    sealed class UiEvent {
        data class ShowSnackbar(val uiText: UiText) : UiEvent()
        data object NavigateToHomeScreen : UiEvent()
    }
}