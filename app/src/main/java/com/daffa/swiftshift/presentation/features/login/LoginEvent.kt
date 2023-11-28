package com.daffa.swiftshift.presentation.features.login

import com.daffa.swiftshift.presentation.util.state.SelectionOption

sealed class LoginEvent {
    data class SelectRole(val selectionOption: SelectionOption<String>) : LoginEvent()
    data class EnterEmail(val value: String) : LoginEvent()
    data class EnterPassword(val value: String) : LoginEvent()
    data object TogglePasswordVisibility : LoginEvent()
    data object Login : LoginEvent()
}