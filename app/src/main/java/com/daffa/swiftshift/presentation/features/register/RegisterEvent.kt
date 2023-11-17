package com.daffa.swiftshift.presentation.features.register

sealed class RegisterEvent {
    data class EnteredEmailAddress(val value: String) : RegisterEvent()
    data class EnteredFullName(val value: String) : RegisterEvent()
    data class EnteredPassword(val value: String) : RegisterEvent()
    data class EnteredConfirmPassword(val value: String) : RegisterEvent()
    data class ProceedNextScreen(val destinationIndex: Int) : RegisterEvent()
    data object TogglePasswordVisibility : RegisterEvent()
    data object ToggleConfirmPasswordVisibility : RegisterEvent()
    data object InsertProfilePicture : RegisterEvent()
    data object Register : RegisterEvent()
    data object RegisterWithoutProfilePicture : RegisterEvent()
}