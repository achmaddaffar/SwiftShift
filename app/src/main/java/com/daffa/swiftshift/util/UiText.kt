package com.daffa.swiftshift.util

import androidx.annotation.StringRes
import com.daffa.swiftshift.R

sealed class UiText {
    data class DynamicString(val value: String): UiText()
    data class StringResource(@StringRes val id: Int): UiText()

    companion object {
        fun unknownError(): UiText {
            return UiText.StringResource(R.string.error_uknown)
        }
    }
}