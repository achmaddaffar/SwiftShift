package com.daffa.swiftshift.presentation.features.gig_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daffa.swiftshift.R
import com.daffa.swiftshift.domain.use_case.gig.GetGigDetailUseCase
import com.daffa.swiftshift.util.Resource
import com.daffa.swiftshift.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GigDetailViewModel @Inject constructor(
    private val getGigDetailUseCase: GetGigDetailUseCase,
) : ViewModel() {

    private val _state = mutableStateOf(GigDetailState())
    val state: State<GigDetailState> = _state

    private val channel = Channel<UiEvent>()
    val eventFlow = channel.receiveAsFlow()

    fun fetch(gigId: String) {
        viewModelScope.launch {
            getGigDetailUseCase(gigId).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        channel.send(
                            UiEvent.ShowSnackBar(
                                UiText.StringResource(R.string.error_couldnt_reach_server)
                            )
                        )
                        _state.value = state.value.copy(
                            isLoading = false,
                            data = null
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true,
                            data = null
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            data = result.data
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val uiText: UiText) : UiEvent()
    }
}