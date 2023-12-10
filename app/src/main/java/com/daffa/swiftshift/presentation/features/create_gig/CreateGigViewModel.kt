package com.daffa.swiftshift.presentation.features.create_gig

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daffa.swiftshift.data.remote.request.CreateGigRequest
import com.daffa.swiftshift.domain.use_case.gig.CreateGigUseCase
import com.daffa.swiftshift.presentation.util.state.BaseTextFieldState
import com.daffa.swiftshift.util.DateUtil
import com.daffa.swiftshift.util.Resource
import com.daffa.swiftshift.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGigViewModel @Inject constructor(
    private val createGigUseCase: CreateGigUseCase,
) : ViewModel() {

    private val _titleState = mutableStateOf(BaseTextFieldState())
    val titleState: State<BaseTextFieldState> = _titleState

    private val _imageState = mutableStateOf<Uri?>(null)
    val imageState: State<Uri?> = _imageState

    private val _tagState = mutableStateOf(BaseTextFieldState())
    val tagState: State<BaseTextFieldState> = _tagState

    private val _descriptionState = mutableStateOf(BaseTextFieldState())
    val descriptionState: State<BaseTextFieldState> = _descriptionState

    private val _maxApplierState = mutableStateOf(BaseTextFieldState())
    val maxApplierState: State<BaseTextFieldState> = _maxApplierState

    private val _salaryState = mutableStateOf(BaseTextFieldState())
    val salaryState: State<BaseTextFieldState> = _salaryState

    private val _dateState = mutableStateOf(BaseTextFieldState())
    val dateState: State<BaseTextFieldState> = _dateState

    private val _date = mutableStateOf<Long>(0)
    val date: State<Long> = _date

    private val channel = Channel<UiEvent>()
    val eventFlow = channel.receiveAsFlow()

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun onEvent(event: CreateGigEvent) {
        when (event) {
            is CreateGigEvent.EnterDueDate -> {
                _dateState.value = dateState.value.copy(
                    text = DateUtil.convertMillisecondToDateFormat(event.value)
                )
                _date.value = event.value
            }

            is CreateGigEvent.EnterGigTitle -> {
                _titleState.value = titleState.value.copy(
                    text = event.value
                )
            }

            is CreateGigEvent.EnterMaxApplier -> {
                _maxApplierState.value = maxApplierState.value.copy(
                    text = event.value.toString()
                )
            }

            is CreateGigEvent.EnterSalary -> {
                _salaryState.value = salaryState.value.copy(
                    text = event.value.toString()
                )
            }

            is CreateGigEvent.EnterTag -> {
                _tagState.value = tagState.value.copy(
                    text = event.value
                )
            }

            is CreateGigEvent.CropImage -> {
                _imageState.value = null
                _imageState.value = event.uri
            }

            CreateGigEvent.RemoveImage -> {
                _imageState.value = null
            }

            is CreateGigEvent.PickImage -> {
                _imageState.value = null
                _imageState.value = event.uri
            }

            is CreateGigEvent.EnterDescription -> {
                _descriptionState.value = descriptionState.value.copy(
                    text = event.value
                )
            }

            is CreateGigEvent.Post -> {
                val request = CreateGigRequest(
                    title = titleState.value.text,
                    description = descriptionState.value.text,
                    tag = tagState.value.text,
                    maxApplier = maxApplierState.value.text.toInt(),
                    deadlineDate = date.value,
                    salary = salaryState.value.text.toDouble(),
                    latitude = event.lat,
                    longitude = event.lon
                )
                viewModelScope.launch {
                    createGigUseCase(
                        request = request,
                        imageUri = imageState.value
                    ).collect { result ->
                        when (result) {
                            is Resource.Error -> {
                                _isLoading.value = false
                                result.uiText?.let {
                                    channel.send(
                                        UiEvent.ShowSnackBar(it)
                                    )
                                }
                            }

                            is Resource.Loading -> {
                                _isLoading.value = true
                            }

                            is Resource.Success -> {
                                _isLoading.value = false
                                viewModelScope.launch {
                                    channel.send(
                                        UiEvent.NavigateBack
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    fun isButtonEnabled(): Boolean {
        return titleState.value.text.isNotEmpty() &&
                imageState.value != null &&
                tagState.value.text.isNotEmpty() &&
                descriptionState.value.text.isNotEmpty() &&
                maxApplierState.value.text.isNotEmpty() &&
                salaryState.value.text.isNotEmpty() &&
                dateState.value.text.isNotEmpty()
    }

    sealed class UiEvent {
        data class ShowSnackBar(val uiText: UiText) : UiEvent()
        data object NavigateBack : UiEvent()
    }
}