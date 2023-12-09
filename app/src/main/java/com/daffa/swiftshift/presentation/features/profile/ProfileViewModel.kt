package com.daffa.swiftshift.presentation.features.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daffa.swiftshift.R
import com.daffa.swiftshift.data.remote.response.GigProviderResponse
import com.daffa.swiftshift.data.remote.response.GigWorkerResponse
import com.daffa.swiftshift.domain.use_case.auth.GetRoleUseCase
import com.daffa.swiftshift.domain.use_case.auth.LogoutUseCase
import com.daffa.swiftshift.domain.use_case.gig_provider.GetProfileGigProviderUseCase
import com.daffa.swiftshift.domain.use_case.gig_worker.GetProfileGigWorkerUseCase
import com.daffa.swiftshift.util.Resource
import com.daffa.swiftshift.util.Role
import com.daffa.swiftshift.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getRoleUseCase: GetRoleUseCase,
    private val getProfileGigWorkerUseCase: GetProfileGigWorkerUseCase,
    private val getProfileGigProviderUseCase: GetProfileGigProviderUseCase,
) : ViewModel() {

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _gigWorker = mutableStateOf<GigWorkerResponse?>(null)
    val gigWorker: State<GigWorkerResponse?> = _gigWorker

    private val _gigProvider = mutableStateOf<GigProviderResponse?>(null)
    val gigProvider: State<GigProviderResponse?> = _gigProvider

    private val channel = Channel<UiEvent>()
    val uiFlow = channel.receiveAsFlow()

    init {
        getProfile()
    }

    fun getRole(): Role {
        return getRoleUseCase()!!
    }

    private fun getProfile() {
        when (getRole()) {
            Role.GigProvider -> {
                viewModelScope.launch {
                    getProfileGigProviderUseCase().collect { result ->
                        when (result) {
                            is Resource.Error -> {
                                channel.send(
                                    UiEvent.ShowSnackBar(
                                        UiText.StringResource(R.string.error_couldnt_reach_server)
                                    )
                                )
                                _isLoading.value = false
                            }

                            is Resource.Loading -> {
                                _isLoading.value = true
                            }

                            is Resource.Success -> {
                                _isLoading.value = false
                                _gigProvider.value = result.data
                            }
                        }
                    }
                }
            }

            Role.GigWorker -> {
                viewModelScope.launch {
                    getProfileGigWorkerUseCase().collect { result ->
                        when (result) {
                            is Resource.Error -> {
                                channel.send(
                                    UiEvent.ShowSnackBar(
                                        UiText.StringResource(R.string.error_couldnt_reach_server)
                                    )
                                )
                                _isLoading.value = false
                            }

                            is Resource.Loading -> {
                                _isLoading.value = true
                            }

                            is Resource.Success -> {
                                _isLoading.value = false
                                _gigWorker.value = result.data
                            }
                        }
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