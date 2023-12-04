package com.daffa.swiftshift.presentation.features.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daffa.swiftshift.R
import com.daffa.swiftshift.domain.use_case.gig.SearchGigUseCase
import com.daffa.swiftshift.presentation.util.state.BaseTextFieldState
import com.daffa.swiftshift.util.Constants
import com.daffa.swiftshift.util.Constants.Empty
import com.daffa.swiftshift.util.Resource
import com.daffa.swiftshift.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchGigUseCase: SearchGigUseCase,
) : ViewModel() {

    private val _query = mutableStateOf(String.Empty)
    val query: State<String> = _query

    private val _state = mutableStateOf(SearchGigState())
    val state: State<SearchGigState> = _state

    private val channel = Channel<UiEvent>()
    val eventFlow = channel.receiveAsFlow()

    private var searchJob: Job? = null

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.EnteredSearchQuery -> {
                _query.value = event.value
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(Constants.SEARCH_DELAY_DURATION)
                    searchGigUseCase(event.value)
                        .onEach { result ->
                            when (result) {
                                is Resource.Error -> {
                                    _state.value = state.value.copy(
                                        gigItems = emptyList(),
                                        isLoading = false
                                    )
                                    channel.send(UiEvent.ShowSnackBar(UiText.StringResource(R.string.error_couldnt_reach_server)))
                                }

                                is Resource.Loading -> {
                                    _state.value = state.value.copy(
                                        gigItems = emptyList(),
                                        isLoading = true
                                    )
                                }

                                is Resource.Success -> {
                                    _state.value = state.value.copy(
                                        gigItems = result.data ?: emptyList(),
                                        isLoading = false
                                    )
                                }
                            }
                        }
                        .launchIn(this)
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val uiText: UiText) : UiEvent()
    }
}