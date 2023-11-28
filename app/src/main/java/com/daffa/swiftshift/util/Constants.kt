package com.daffa.swiftshift.util

object Constants {
    const val BASE_URL = "http://10.0.2.2:8005/"
    const val PREFERENCES_NAME = "SwiftShift_Preferences"
    const val KEY_JWT_TOKEN = "jwt_token"

    const val SPLASH_SCREEN_DURATION = 250L
    const val MIN_PASSWORD_LENGTH = 3
    const val GIG_WORKER = "gigWorker"
    const val GIG_PROVIDER = "gigProvider"

    const val PROFILE_PICTURE_DATA = "profile_picture_data"
    const val CREATE_ACCOUNT_PART_DATA = "create_account_data"
    const val UPDATE_PROFILE_PART_DATA = "update_profile_data"
    const val CREATE_GIG_PART_DATA = "create_gig_data"

    val String.Companion.Empty
        inline get() = ""
}