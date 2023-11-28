package com.daffa.swiftshift.presentation.features.register

import android.net.Uri
import com.daffa.swiftshift.presentation.util.state.SelectionOption

sealed class RegisterEvent {
    data class EnterEmailAddress(val value: String) : RegisterEvent()
    data class EnterFullName(val value: String) : RegisterEvent()
    data class EnterPassword(val value: String) : RegisterEvent()
    data class EnterConfirmPassword(val value: String) : RegisterEvent()
    data class ProceedNextScreen(val destinationIndex: Int) : RegisterEvent()
    data class SelectRole(val selectionOption: SelectionOption<String>) : RegisterEvent()
    data object TogglePasswordVisibility : RegisterEvent()
    data object ToggleConfirmPasswordVisibility : RegisterEvent()
    data class PickImage(val uri: Uri?) : RegisterEvent()
    data class CropImage(val uri: Uri?) : RegisterEvent()
    data object RemovePhoto : RegisterEvent()
    data object Register : RegisterEvent()
}