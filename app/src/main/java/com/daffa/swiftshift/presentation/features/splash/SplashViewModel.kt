package com.daffa.swiftshift.presentation.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daffa.swiftshift.R
import com.daffa.swiftshift.domain.use_case.auth.AuthenticateUseCase
import com.daffa.swiftshift.util.Resource
import com.daffa.swiftshift.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authenticate: AuthenticateUseCase,
) : ViewModel() {

    private val channel = Channel<UiEvent>()
    val eventFlow = channel.receiveAsFlow()

    fun auth() {
        viewModelScope.launch {
            authenticate().collect { result ->
                when (result) {
                    is Resource.Loading -> Unit

                    is Resource.Error -> {
                        channel.send(UiEvent.NavigateToOnBoarding)
                    }

                    is Resource.Success -> {
                        channel.send(UiEvent.NavigateToHome)
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowToast(val uiText: UiText) : UiEvent()
        data object NavigateToOnBoarding : UiEvent()
        data object NavigateToHome : UiEvent()
    }
}