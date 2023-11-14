package com.daffa.swiftshift.presentation.navigation.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigation
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftNavigationActions
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftTopLevelDestination
import com.daffa.swiftshift.presentation.navigation.util.TOP_LEVEL_DESTINATION

@Composable
fun SwiftShiftScaffold(
    navigationActions: SwiftShiftNavigationActions,
    modifier: Modifier = Modifier,
    showBottomBar: Boolean = true,
    state: ScaffoldState,
    showToolbar: Boolean = true,
    toolbarTitle: String? = null,
    showBackArrow: Boolean = false,
    navActions: @Composable RowScope.() -> Unit = {},
    bottomNavItem: List<SwiftShiftTopLevelDestination> = TOP_LEVEL_DESTINATION,
    onFabClick: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        bottomBar = {
            if (showBottomBar)
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth(),
                    backgroundColor = MaterialTheme.colorScheme.surface
                ) {
                    BottomNavigation(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        bottomNavItem.forEach { bottomNavItem ->
                            SwiftShiftBottomNavItem(
                                selectedIconId = bottomNavItem.selectedIconId,
                                unselectedIconId = bottomNavItem.unselectedIconId,
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