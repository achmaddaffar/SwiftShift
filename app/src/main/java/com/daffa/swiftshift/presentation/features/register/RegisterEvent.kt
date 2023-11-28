package com.daffa.swiftshift.presentation.features.register

import android.net.Uri
import com.daffa.swiftshift.presentation.util.state.SelectionOption

sealed class RegisterEvent {
    data class EnteredEmailAddress(val value: String) : RegisterEvent()
    data class EnteredFullName(val value: String) : RegisterEvent()
    data class EnteredPassword(val value: String) : RegisterEvent()
    data class EnteredConfirmPassword(val value: String) : RegisterEvent()
    data class ProceedNextScreen(val destinationIndex: Int) : RegisterEvent()
    data class SelectedRole(val selectionOption: SelectionOption<String>) : RegisterEvent()
    data object TogglePasswordVisibility : RegisterEvent()
    data object ToggleConfirmPasswordVisibility : RegisterEvent()
    data class PickImage(val uri: Uri?) : RegisterEvent()
    data class CropImage(val uri: Uri?) : RegisterEvent()
    data object RemovePhoto : RegisterEvent()
    data object Register : RegisterEvent()
}