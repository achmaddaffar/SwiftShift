package com.daffa.swiftshift.presentation.util.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SelectionOption<T>(val option: T, var initialSelectedValue: Boolean) {
    var selected by mutableStateOf(initialSelectedValue)
}