package com.daffa.swiftshift.presentation.features.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.daffa.swiftshift.R

sealed class OnBoardingPage(
    @DrawableRes val imageId: Int,
    @StringRes val imageContentDesc: Int,
    @StringRes val titleId: Int,
    @StringRes val descriptionId: Int,
) {
    data object FirstPage : OnBoardingPage(
        imageId = R.drawable.onboarding_1,
        imageContentDesc = R.string.onboarding_1_content_description,
        titleId = R.string.onboarding_1_title,
        descriptionId = R.string.onboarding_1_description
    )

    data object SecondPage : OnBoardingPage(
        imageId = R.drawable.onboarding_2,
        imageContentDesc = R.string.onboarding_2_content_description,
        titleId = R.string.onboarding_2_title,
        descriptionId = R.string.onboarding_2_description
    )

    data object ThirdPage : OnBoardingPage(
        imageId = R.drawable.onboarding_3,
        imageContentDesc = R.string.onboarding_3_content_description,
        titleId = R.string.onboarding_3_title,
        descriptionId = R.string.onboarding_3_description
    )
}