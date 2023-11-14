package com.daffa.swiftshift.presentation.navigation.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SwiftShiftTopLevelDestination(
    val route: String,
    @DrawableRes val unselectedIconId: Int,
    @DrawableRes val selectedIconId: Int,
    @StringRes val iconTextId: Int,
    @StringRes val labelId: Int
)