package com.daffa.swiftshift.presentation.navigation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.daffa.swiftshift.presentation.navigation.util.SwiftShiftTopLevelDestination
import com.daffa.swiftshift.presentation.navigation.util.TOP_LEVEL_DESTINATION

@Composable
fun SwiftShiftBottomNavigationBar(
    selectedDestination: String,
    navigateToTopLevelDestination: (SwiftShiftTopLevelDestination) -> Unit,
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        TOP_LEVEL_DESTINATION.forEach { destination ->
            NavigationBarItem(
                selected = selectedDestination == destination.route,
                onClick = {
                    navigateToTopLevelDestination(destination)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = if (selectedDestination == destination.route) destination.selectedIconId else destination.unselectedIconId),
                        contentDescription = stringResource(
                            id = destination.iconTextId
                        )
                    )
                }
            )
        }
    }
}