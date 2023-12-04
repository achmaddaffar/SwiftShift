package com.daffa.swiftshift.presentation.features.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daffa.swiftshift.R
import com.daffa.swiftshift.domain.use_case.auth.LogoutUseCase
import com.daffa.swiftshift.domain.use_case.gig_worker.GetProfileGigWorkerUseCase
import com.daffa.swiftshift.util.Resource
import com.daffa.swiftshift.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getProfileGigWorkerUseCase: GetProfileGigWorkerUseCase
): ViewModel() {

    private val _state = mutableStateOf(ProfileGigWorkerState())
    val state: State<ProfileGigWorkerState> = _state

    private val channel = Channel<UiEvent>()
    val uiFlow = channel.receiveAsFlow()

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            getProfileGigWorkerUseCase().collect { result ->
                when(result) {
                    is Resource.Error -> {
                        channel.send(
                            UiEvent.ShowSnackBar(
                                UiText.StringResource(R.string.error_couldnt_reach_server)
                            )
                        )
                        _state.value = state.value.copy(
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            gigWorker = result.data
                        )
                    }
                }
            }
        }
    }

    fun logout() {
        logoutUseCase()
    }

    sealed class UiEvent {
        data class ShowSnackBar(val uiText: UiText) : UiEvent()
    }
}