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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateGigViewModel @Inject constructor(
    private val createGigUseCase: CreateGigUseCase
) : ViewModel() {

    private val _titleState = mutableStateOf(BaseTextFieldState())
    val titleState: State<BaseTextFieldState> = _titleState

    private val _imageState = mutableStateOf<Uri>(Uri.EMPTY)
    val imageState: State<Uri> = _imageState

    private val _tagState = mutableStateOf(BaseTextFieldState())
    val tagState: State<BaseTextFieldState> = _tagState

    private val _maxApplierState = mutableStateOf(BaseTextFieldState())
    val maxApplierState: State<BaseTextFieldState> = _maxApplierState

    private val _salaryState = mutableStateOf(BaseTextFieldState())
    val salaryState: State<BaseTextFieldState> = _salaryState

    private val _dateState = mutableStateOf(BaseTextFieldState())
    val dateState: State<BaseTextFieldState> = _dateState

    fun onEvent(event: CreateGigEvent) {
        when (event) {
            is CreateGigEvent.EnterDueDate -> {

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

            is CreateGigEvent.InsertImage -> {

            }

            is CreateGigEvent.CropImage -> {

            }

            CreateGigEvent.Post -> {
                viewModelScope.launch {

                }
            }
        }
    }
}