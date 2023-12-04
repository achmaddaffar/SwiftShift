package com.daffa.swiftshift.util

object Constants {
//    const val BASE_URL = "http://127.0.0.1:8005/"
    const val BASE_URL = "http://3.229.102.242:8080/"

    const val PREFERENCES_NAME = "SwiftShift_Preferences"
    const val GIG_DATABASE_NAME = "gig.db"

    const val SEARCH_DELAY_DURATION = 1000L
    const val SPLASH_SCREEN_DURATION = 250L
    const val MIN_PASSWORD_LENGTH = 3

    const val KEY_JWT_TOKEN = "jwt_token"
    const val KEY_ROLE = "role"
    const val KEY_EMAIL = "email"

    const val GIG_WORKER = "gigWorker"
    const val GIG_PROVIDER = "gigProvider"

    const val PROFILE_PICTURE_DATA = "profile_picture_data"
    const val CREATE_ACCOUNT_PART_DATA = "create_account_data"
    const val UPDATE_PROFILE_PART_DATA = "update_profile_data"
    const val CREATE_GIG_PART_DATA = "create_gig_data"

    const val DEFAULT_NEARBY_GIGS_PAGE_SIZE = 15
    const val DEFAULT_REVIEWS_TO_GIG_PROVIDER_SIZE = 15

    val String.Companion.Empty
        inline get() = ""
}