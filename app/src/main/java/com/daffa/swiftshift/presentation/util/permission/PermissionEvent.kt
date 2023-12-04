package com.daffa.swiftshift.presentation.util.permission

sealed class PermissionEvent {
    data object Granted : PermissionEvent()
    data object Revoked : PermissionEvent()
}
