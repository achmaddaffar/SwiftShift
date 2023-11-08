package com.daffa.swiftshift.presentation.navigation.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.navigation.SwiftShiftTopLevelDestination
import com.daffa.swiftshift.presentation.navigation.TOP_LEVEL_DESTINATION
import com.daffa.swiftshift.presentation.navigation.util.LayoutType
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftNavigationContentPosition
import com.daffa.swiftshift.presentation.navigation.util.navigationMeasurePolicy

@Composable
fun SwiftShiftNavigationRail(
    selectedDestination: String,
    navigationContentPosition: SwiftShiftNavigationContentPosition,
    navigateToTopLevelDestination: (SwiftShiftTopLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit = {},
) {
    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        Layout(
            modifier = Modifier.widthIn(max = 80.dp),
            content = {
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    NavigationRailItem(
                        selected = false,
                        onClick = onDrawerClicked,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(id = R.string.navigation_drawer)
                            )
                        }
                    )

                    Column(
                        modifier = Modifier.layoutId(LayoutType.CONTENT),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        TOP_LEVEL_DESTINATION.forEach { destination ->
                            NavigationRailItem(
                                selected = selectedDestination == destination.route,
                                onClick = {
                                    navigateToTopLevelDestination(destination)
                                },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = if (selectedDestination == destination.route) destination.selectedIconId else destination.unselectedIconId),
                                        contentDescription = stringResource(id = destination.iconTextId)
                                    )
                                }
                            )
                        }
                    }
                }
            },
            measurePolicy = navigationMeasurePolicy(navigationContentPosition)
        )
    }
}