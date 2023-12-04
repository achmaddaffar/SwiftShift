package com.daffa.swiftshift.domain.util

import android.util.Patterns
import com.daffa.swiftshift.util.Constants
import com.daffa.swiftshift.util.error.AuthError

object ValidationUtil {
    fun validateEmail(email: String): AuthError? {
        val trimmedEmail = email.trim()

        if (trimmedEmail.isBlank())
            return AuthError.FieldEmpty
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return AuthError.InvalidEmail
        return null
    }

    fun validateFullName(fullName: String): AuthError? {
        val trimmedUsername = fullName.trim()

        if (trimmedUsername.isBlank())
            return AuthError.FieldEmpty
        return null
    }

    fun validatePassword(password: String): AuthError? {
        val capitalLetterInPassword = password.any { it.isUpperCase() }
        val numberInPassword = password.any { it.isDigit() }
        if (password.isBlank())
            return AuthError.FieldEmpty
        if (password.length < Constants.MIN_PASSWORD_LENGTH)
            return AuthError.InputTooShort
        if (!capitalLetterInPassword || !numberInPassword)
            return AuthError.InvalidPassword
        return null
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): AuthError? {
        return if (password != confirmPassword) AuthError.PasswordDoesNotMatch else null
    }
}