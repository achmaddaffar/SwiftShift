package com.daffa.swiftshift.presentation.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.window.layout.DisplayFeature
import com.daffa.swiftshift.presentation.navigation.component.SwiftShiftBottomNavigationBar
import com.daffa.swiftshift.presentation.navigation.component.SwiftShiftNavigationRail
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftContentType
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftNavigationContentPosition
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftNavigationType

@Composable
fun SwiftShiftAppContent(
    modifier: Modifier = Modifier,
    navigationType: SwiftShiftNavigationType,
    contentType: SwiftShiftContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: SwiftShiftNavigationContentPosition,
    navController: NavHostController,
    selectedDestination: String,
    navigateToTopLevelDestination: (SwiftShiftTopLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxSize()
    ) {
        AnimatedVisibility(visible = navigationType == SwiftShiftNavigationType.NAVIGATION_RAIL) {
            SwiftShiftNavigationRail(
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                navigateToTopLevelDestination = navigateToTopLevelDestination,
                onDrawerClicked = onDrawerClicked
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            Navigation(
                navController = navController,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationType = navigationType,
                modifier = Modifier.weight(1f)
            )
            AnimatedVisibility(visible = navigationType == SwiftShiftNavigationType.BOTTOM_NAVIGATION) {
                SwiftShiftBottomNavigationBar(
                    selectedDestination = selectedDestination,
                    navigateToTopLevelDestination = navigateToTopLevelDestination
                )
            }
        }
    }
}