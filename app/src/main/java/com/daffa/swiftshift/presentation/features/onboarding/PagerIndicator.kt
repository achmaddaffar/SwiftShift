package com.daffa.swiftshift.presentation.features.onboarding

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.ui.theme.HintGray

@Composable
fun PagerIndicator(
    item: List<OnBoardingPage>,
    currentPage: Int,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(top = 20.dp)
    ) {
        repeat(item.size) {
            Indicator(
                isSelected = it == currentPage,
                color = selectedColor
            )
        }
    }
}

@Composable
fun Indicator(
    isSelected: Boolean,
    color: Color,
) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 48.dp else 12.dp,
        label = stringResource(R.string.horizontal_pager_indicator_label)
    )

    Box(
        modifier = Modifier
            .padding(4.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) color else HintGray
            )
    )
}