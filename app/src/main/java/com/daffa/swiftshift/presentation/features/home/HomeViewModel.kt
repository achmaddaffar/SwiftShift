package com.daffa.swiftshift.presentation.features.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daffa.swiftshift.R
import com.daffa.swiftshift.data.remote.response.GigWorkerResponse
import com.daffa.swiftshift.domain.model.Gig
import com.daffa.swiftshift.domain.use_case.gig.GetNearbyGigsPreviewUseCase
import com.daffa.swiftshift.domain.use_case.gig.GetRecommendedGigsUseCase
import com.daffa.swiftshift.domain.use_case.gig_worker.GetProfileGigWorkerUseCase
import com.daffa.swiftshift.domain.use_case.location.GetCurrentLocationUseCase
import com.daffa.swiftshift.presentation.util.permission.PermissionEvent
import com.daffa.swiftshift.util.Resource
import com.daffa.swiftshift.util.UiText
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.S)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProfileGigWorkerUseCase: GetProfileGigWorkerUseCase,
    private val getNearbyGigsPreviewUseCase: GetNearbyGigsPreviewUseCase,
    private val getRecommendedGigsUseCase: GetRecommendedGigsUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
) : ViewModel() {

    private val channel = Channel<UiEvent>()
    val eventFlow = channel.receiveAsFlow()

    private val _state = MutableStateFlow<Resource<LatLng?>>(Resource.Loading())
    val state = _state.asStateFlow()

    private val _gigWorker = mutableStateOf<GigWorkerResponse?>(null)
    val gigWorker: State<GigWorkerResponse?> = _gigWorker

    private val _nearbyGigsPreview = mutableStateOf<List<Gig>>(emptyList())
    val nearbyGigsPreview: State<List<Gig>> = _nearbyGigsPreview

    private val _recommendedGigs = mutableStateOf<List<Gig>>(emptyList())
    val recommendedGigs: State<List<Gig>> = _recommendedGigs

    fun handle(event: PermissionEvent) {
        when (event) {
            PermissionEvent.Granted -> {
                viewModelScope.launch {
                    getCurrentLocationUseCase.invoke().collect { location ->
                        _state.value = Resource.Success(location)
                    }
                }
            }

            PermissionEvent.Revoked -> {
                _state.value = Resource.Error()
            }
        }
    }

    fun getNearbyGigsPreview(latLng: LatLng) {
        println("latitude GetNearbyGigsPreview: ${latLng.latitude}")
        println("longitude GetNearbyGigsPreview: ${latLng.longitude}")
        viewModelScope.launch {
            getNearbyGigsPreviewUseCase(latLng).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        result.uiText?.let {
                            channel.send(UiEvent.ShowSnackbar(it))
                        }
                    }

                    is Resource.Loading -> {
                        _nearbyGigsPreview.value = emptyList()
                    }

                    is Resource.Success -> {
                        _nearbyGigsPreview.value = result.data!!
                    }
                }
            }
        }
    }

    fun getRecommendedGigs(latLng: LatLng) {
        viewModelScope.launch {
            getRecommendedGigsUseCase(latLng).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        result.uiText?.let {
                            channel.send(UiEvent.ShowSnackbar(it))
                        }
                    }

                    is Resource.Loading -> {
                        _recommendedGigs.value = emptyList()
                    }

                    is Resource.Success -> {
                        _recommendedGigs.value = result.data!!
                    }
                }
            }
        }
    }

    fun getProfile() {
        viewModelScope.launch {
            getProfileGigWorkerUseCase().collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _gigWorker.value = null
                        channel.send(UiEvent.ShowSnackbar(UiText.StringResource(R.string.error_couldnt_reach_server)))
                    }

                    is Resource.Loading -> {
                        _gigWorker.value = null
                    }

                    is Resource.Success -> {
                        _gigWorker.value = result.data
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val uiText: UiText) : UiEvent()
    }
}