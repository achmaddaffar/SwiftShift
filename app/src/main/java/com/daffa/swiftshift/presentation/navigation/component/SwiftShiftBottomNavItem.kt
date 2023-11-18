package com.daffa.swiftshift.presentation.navigation.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.daffa.swiftshift.presentation.ui.theme.HintGray
import com.daffa.swiftshift.presentation.ui.theme.Primary600
import com.daffa.swiftshift.presentation.ui.theme.SpaceSmall
import com.daffa.swiftshift.util.Constants.Empty

@Composable
fun RowScope.SwiftShiftBottomNavItem(
    modifier: Modifier = Modifier,
    selectedIconId: Int,
    unselectedIconId: Int,
    selected: Boolean,
    contentDescription: String? = null,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = HintGray,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    val lineLength = animateFloatAsState(
        targetValue = if (selected) 1f else 0f,
        animationSpec = tween(
            durationMillis = 400
        ),
        label = String.Empty
    )

    BottomNavigationItem(
        selected = selected,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        selectedContentColor = selectedColor,
        unselectedContentColor = unselectedColor,
        icon = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(SpaceSmall)
                    .drawBehind {
                        if (lineLength.value > 0f)
                            drawLine(
                                color = if (selected)
                                    selectedColor
                                else
                                    unselectedColor,
                                start = Offset(
                                    x = size.width / 2f - lineLength.value * 15.dp.toPx(),
                                    y = size.height
                                ),
                                end = Offset(
                                    x = size.width / 2f + lineLength.value * 15.dp.toPx(),
                                    y = size.height
                                ),
                                strokeWidth = 2.dp.toPx(),
                                cap = StrokeCap.Round
                            )
                    }
            ) {
                Icon(
                    painter = painterResource(id = if (selected) selectedIconId else unselectedIconId),
                    contentDescription = contentDescription,
                    modifier = Modifier
                        .align(Alignment.Center),
                    tint = if (selected) selectedColor else unselectedColor
                )
            }
        }
    )
}