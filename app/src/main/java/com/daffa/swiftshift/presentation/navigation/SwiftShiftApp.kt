package com.daffa.swiftshift.presentation.navigation

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.daffa.swiftshift.presentation.navigation.util.DevicePosture
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftContentType
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftNavigationContentPosition
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftNavigationType
import com.daffa.swiftshift.presentation.navigation.util.isBookPosture
import com.daffa.swiftshift.presentation.navigation.util.isSeparating

@Composable
fun SwiftShiftApp(
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
) {
    val navigationType: SwiftShiftNavigationType
    val contentType: SwiftShiftContentType

    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

    val foldingDevicePosture = when {
        isBookPosture(foldingFeature) ->
            DevicePosture.BookPosture(foldingFeature.bounds)

        isSeparating(foldingFeature) ->
            DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

        else -> DevicePosture.NormalPosture
    }

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            navigationType = SwiftShiftNavigationType.BOTTOM_NAVIGATION
            contentType = SwiftShiftContentType.SINGLE_PANE
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = SwiftShiftNavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                SwiftShiftContentType.DUAL_PANE
            } else {
                SwiftShiftContentType.SINGLE_PANE
            }
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                SwiftShiftNavigationType.NAVIGATION_RAIL
            } else {
                SwiftShiftNavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = SwiftShiftContentType.DUAL_PANE
        }

        else -> {
            navigationType = SwiftShiftNavigationType.BOTTOM_NAVIGATION
            contentType = SwiftShiftContentType.SINGLE_PANE
        }
    }

    val navigationContentPosition = when (windowSize.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            SwiftShiftNavigationContentPosition.TOP
        }

        WindowHeightSizeClass.Medium, WindowHeightSizeClass.Expanded -> {
            SwiftShiftNavigationContentPosition.CENTER
        }

        else -> {
            SwiftShiftNavigationContentPosition.TOP
        }
    }

    SwiftShiftNavigationWrapper(
        navigationType = navigationType,
        contentType = contentType,
        displayFeatures = displayFeatures,
        navigationContentPosition = navigationContentPosition
    )
}
