package com.daffa.swiftshift.presentation.features.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.daffa.swiftshift.presentation.util.state.BaseTextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

): ViewModel() {

    private val _searchState = mutableStateOf(BaseTextFieldState())
    val searchState: State<BaseTextFieldState> = _searchState

    fun onEvent(event: SearchEvent) {
        when(event) {
            is SearchEvent.EnteredSearchQuery -> {
                _searchState.value = _searchState.value.copy(
                    text = event.value
                )
            }
        }
    }
}