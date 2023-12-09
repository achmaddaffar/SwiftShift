package com.daffa.swiftshift.presentation.features.create_gig

import android.net.Uri

sealed class CreateGigEvent {
    data class EnterGigTitle(val value: String) : CreateGigEvent()
    data class EnterTag(val value: String) : CreateGigEvent()
    data class EnterMaxApplier(val value: Int) : CreateGigEvent()
    data class EnterSalary(val value: Double) : CreateGigEvent()
    data class EnterDueDate(val value: Long) : CreateGigEvent()
    data class InsertImage(val value: Uri?) : CreateGigEvent()
    data class CropImage(val uri: Uri?) : CreateGigEvent()
    data object Post : CreateGigEvent()
}