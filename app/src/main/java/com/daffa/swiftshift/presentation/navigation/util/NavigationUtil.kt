package com.daffa.swiftshift.presentation.navigation.util

import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.offset

fun navigationMeasurePolicy(
    navigationContentPosition: SwiftShiftNavigationContentPosition,
): MeasurePolicy {
    return MeasurePolicy { measurables, constraints ->
        lateinit var headerMeasurable: Measurable
        lateinit var contentMeasurable: Measurable

        measurables.forEach {
            when (it.layoutId) {
                LayoutType.HEADER -> headerMeasurable = it
                LayoutType.CONTENT -> contentMeasurable = it
                else -> error("Unknown layoutId encountered!")
            }
        }

        val headerPlaceable = headerMeasurable.measure(constraints)
        val contentPlaceable =
            contentMeasurable.measure(constraints.offset(vertical = -headerPlaceable.height))
        layout(constraints.maxWidth, constraints.maxHeight) {
            headerPlaceable.placeRelative(0, 0)

            val nonContentVerticalSpace = constraints.maxHeight - contentPlaceable.height
            val contentPlaceableY = when (navigationContentPosition) {
                SwiftShiftNavigationContentPosition.TOP -> 0
                SwiftShiftNavigationContentPosition.CENTER -> nonContentVerticalSpace / 2
            }
                .coerceAtLeast(headerPlaceable.height)

            contentPlaceable.placeRelative(0, contentPlaceableY)
        }
    }
}