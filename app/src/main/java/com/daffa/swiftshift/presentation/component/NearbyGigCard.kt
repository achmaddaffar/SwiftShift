package com.daffa.swiftshift.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.daffa.swiftshift.R
import com.daffa.swiftshift.domain.model.Gig
import com.daffa.swiftshift.presentation.ui.theme.HintGray
import com.daffa.swiftshift.presentation.ui.theme.Slate800
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.ui.theme.SpaceSmall
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.valentinilk.shimmer.shimmer
import okhttp3.internal.format

@Composable
fun NearbyGigCard(
    gig: Gig,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = SpaceSmall),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(SpaceMedium),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.Black),
                ) {
                    Text(
                        text = gig.tag,
                        style = Type.body5Bold(),
                        color = Color.White,
                        modifier = Modifier.padding(4.dp)
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_clock),
                        contentDescription = stringResource(
                            R.string.duration_from_posted
                        ),
                        tint = Slate800
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.just_now),
                        style = Type.body5Regular(),
                        color = Slate800
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                AsyncImage(
                    model = gig.imageUrl,
                    contentDescription = stringResource(R.string.gig_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(0.2f)
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(SpaceMedium)
                ) {
                    Column(
                        modifier = Modifier.align(Alignment.TopStart)
                    ) {
                        Text(
                            text = gig.title,
                            style = Type.body2Bold(),
                            color = Color.Black,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(SpaceSmall))
                        Text(
                            text = gig.gigProviderName,
                            style = Type.body5Regular(),
                            color = Color.Black
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Rp. ${gig.salary}",
                            style = Type.body5Bold(),
                            color = Color.Black
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icon_location_pin),
                                contentDescription = stringResource(R.string.gig_location),
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = format("%.2f Km", gig.distance),
                                style = Type.body5Bold(),
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NearbyGigCardShimmer(
    modifier: Modifier = Modifier
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