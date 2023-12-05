package com.daffa.swiftshift.presentation.features.nearby_gigs

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daffa.swiftshift.R
import com.daffa.swiftshift.domain.use_case.gig.GetNearbyGigsPreviewUseCase
import com.daffa.swiftshift.domain.use_case.gig.GetNearbyGigsUseCase
import com.daffa.swiftshift.util.Resource
import com.daffa.swiftshift.util.UiText
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NearbyGigsViewModel @Inject constructor(
    private val getNearbyGigsUseCase: GetNearbyGigsUseCase,
    private val getNearbyGigsPreviewUseCase: GetNearbyGigsPreviewUseCase,
) : ViewModel() {

//    lateinit var gigsPagingFlow: Flow<PagingData<Gig>>
//    var latitude: Double = 0.0
//    var longitude: Double = 0.0

    private val channel = Channel<UiEvent>()
    val eventFlow = channel.receiveAsFlow()

    private val _state = mutableStateOf(NearbyGigState())
    val state: State<NearbyGigState> = _state

    fun fetch(
        latitude: Double,
        longitude: Double,
    ) {
//        gigsPagingFlow = getNearbyGigsUseCase(
//            latitude,
//            longitude
//        )
        val latLng = LatLng(
            latitude,
            longitude
        )
        viewModelScope.launch {
            getNearbyGigsPreviewUseCase(latLng).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            gigs = emptyList()
                        )
                        channel.send(
                            UiEvent.ShowSnackBar(
                                UiText.StringResource(R.string.error_couldnt_reach_server)
                            )
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            isLoading = true,
                            gigs = emptyList()
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            gigs = result.data!!
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