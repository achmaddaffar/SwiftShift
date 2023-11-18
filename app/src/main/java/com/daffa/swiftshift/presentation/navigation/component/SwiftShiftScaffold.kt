package com.daffa.swiftshift.presentation.navigation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftNavigationActions
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftTopLevelDestination
import com.daffa.swiftshift.presentation.navigation.util.TOP_LEVEL_DESTINATION

@Composable
fun SwiftShiftScaffold(
    navigationActions: SwiftShiftNavigationActions,
    modifier: Modifier = Modifier,
    showBottomBar: Boolean = true,
    state: ScaffoldState,
    bottomNavItem: List<SwiftShiftTopLevelDestination> = TOP_LEVEL_DESTINATION,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        bottomBar = {
            if (showBottomBar)
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colorScheme.surface,
                    cutoutShape = CircleShape,
                    elevation = 6.dp
                ) {
                    BottomNavigation(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        bottomNavItem.forEach { bottomNavItem ->
                            SwiftShiftBottomNavItem(
                                selectedIconId = bottomNavItem.selectedIconId,
                                unselectedIconId = bottomNavItem.unselectedIconId,
                                selected = bottomNavItem.route == navigationActions.getNavController().currentDestination?.route,
                                onClick = {
                                    navigationActions.navigateTo(bottomNavItem)
                                }
                            )
                        }
                    }
                }
        },
        scaffoldState = state,
        modifier = modifier
    ) {
        content(it)
    }
}