package com.daffa.swiftshift.presentation.features.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.ui.theme.HintGray
import com.daffa.swiftshift.presentation.ui.theme.Primary600
import com.daffa.swiftshift.presentation.ui.theme.Primary800
import com.daffa.swiftshift.presentation.ui.theme.SpaceLarge
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.valentinilk.shimmer.shimmer

@Composable
fun HomeTopBanner(
    userFullName: String?,
    profilePictureUrl: String?,
    locationName: String?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.weight(3f)
        ) {
            Text(
                text = stringResource(R.string.welcome) + ",",
                style = Type.heading5Regular()
            )
            userFullName?.let {
                Text(
                    text = it,
                    style = Type.heading3SemiBold(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } ?: kotlin.run {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .height(SpaceMedium * 2)
                        .width(SpaceLarge * 6)
                        .shimmer()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(HintGray)
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_location_pin),
                    contentDescription = stringResource(
                        R.string.location_pin
                    ),
                    tint = Primary600
                )
                Text(
                    text = stringResource(R.string.your_current_location_is),
                    style = Type.body5Regular()
                )
                locationName?.let {
                    Text(
                        text = it,
                        style = Type.body5Regular(),
                        color = Primary800
                    )
                } ?: kotlin.run {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .height(SpaceMedium)
                            .width(SpaceLarge * 2)
                            .shimmer()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(HintGray)
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            profilePictureUrl?.let {
                if (it.isEmpty()) {
                    Image(
                        painter = painterResource(id = R.drawable.img_profile_picture),
                        contentDescription = stringResource(id = R.string.profile_picture),
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color.Red)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    SubcomposeAsyncImage(
                        model = it,
                        contentDescription = stringResource(R.string.profile_picture),
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        loading = {
                            CircularProgressIndicator()
                        }
                    )
                }
            } ?: kotlin.run {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .shimmer()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(HintGray)
                    )
                }
            }
        }
    }
}