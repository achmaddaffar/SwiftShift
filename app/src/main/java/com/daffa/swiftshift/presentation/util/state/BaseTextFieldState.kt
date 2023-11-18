package com.daffa.swiftshift.presentation.util.state

import com.daffa.swiftshift.util.Constants.Empty
import com.daffa.swiftshift.util.error.Error

data class BaseTextFieldState(
    val text: String = String.Empty,
    val error: Error? = null
)