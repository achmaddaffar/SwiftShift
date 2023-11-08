package com.daffa.swiftshift.presentation.navigation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Icon
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.navigation.SwiftShiftTopLevelDestination
import com.daffa.swiftshift.presentation.navigation.TOP_LEVEL_DESTINATION
import com.daffa.swiftshift.presentation.navigation.util.LayoutType
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftNavigationContentPosition
import com.daffa.swiftshift.presentation.navigation.util.navigationMeasurePolicy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalNavigationDrawerContent(
    selectedDestination: String,
    navigationContentPosition: SwiftShiftNavigationContentPosition,
    navigateToTopLevelDestination: (SwiftShiftTopLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit = {},
) {
    ModalDrawerSheet {
        Layout(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .padding(16.dp),
            content = {
                Column(
                    modifier = Modifier.layoutId(LayoutType.HEADER),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name).uppercase(),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        IconButton(onClick = onDrawerClicked) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(
                                    id = R.string.navigation_drawer
                                )
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .layoutId(LayoutType.CONTENT)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TOP_LEVEL_DESTINATION.forEach { destination ->
                            NavigationDrawerItem(
                                selected = selectedDestination == destination.route,
                                label = {
                                    Text(
                                        text = stringResource(id = destination.iconTextId),
                                        modifier = Modifier.padding(horizontal = 16.dp)
                                    )
                                },
                                icon = {
                                    Icon(
                                        painter = painterResource(id = if (selectedDestination == destination.route) destination.selectedIconId else destination.unselectedIconId),
                                        contentDescription = stringResource(id = destination.iconTextId)
                                    )
                                },
                                colors = NavigationDrawerItemDefaults.colors(
                                    unselectedContainerColor = Color.Transparent
                                ),
                                onClick = { navigateToTopLevelDestination(destination) }
                            )
                        }
                    }
                }
            },
            measurePolicy = navigationMeasurePolicy(navigationContentPosition)
        )
    }
}