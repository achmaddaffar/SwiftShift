package com.daffa.swiftshift.presentation.features.create_gig

import android.net.Uri

sealed class CreateGigEvent {
    data class EnterGigTitle(val value: String) : CreateGigEvent()
    data class EnterTag(val value: String) : CreateGigEvent()
    data class EnterDescription(val value: String) : CreateGigEvent()
    data class EnterMaxApplier(val value: String) : CreateGigEvent()
    data class EnterSalary(val value: String) : CreateGigEvent()
    data class EnterDueDate(val value: Long) : CreateGigEvent()
    data class CropImage(val uri: Uri?) : CreateGigEvent()
    data class PickImage(val uri: Uri?) : CreateGigEvent()
    data object RemoveImage : CreateGigEvent()
    data class Post(val lat: Double, val lon: Double) : CreateGigEvent()
}