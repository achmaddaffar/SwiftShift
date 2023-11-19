package com.daffa.swiftshift.presentation.features.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.ui.theme.HintGray
import com.valentinilk.shimmer.shimmer

@Composable
fun AdCard(
    adImageUrl: String?,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
    ) {
        adImageUrl?.let {
            if (adImageUrl.isBlank())
                Box(modifier = modifier.shimmer()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(HintGray)
                    )
                }
            else
                AsyncImage(
                    model = adImageUrl,
                    contentDescription = stringResource(R.string.advertisement_image),
                    contentScale = ContentScale.Crop
                )
        } ?: kotlin.run {
            Box(modifier = modifier.shimmer()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(HintGray)
                )
            }
        }
    }
}