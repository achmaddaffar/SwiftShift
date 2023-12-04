package com.daffa.swiftshift.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.daffa.swiftshift.presentation.ui.theme.HintGray
import com.valentinilk.shimmer.shimmer

@Composable
fun BaseCardShimmer(
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .shimmer()
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(HintGray)
        )
    }
}