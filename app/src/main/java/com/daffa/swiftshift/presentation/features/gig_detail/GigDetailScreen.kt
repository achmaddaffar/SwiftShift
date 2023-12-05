package com.daffa.swiftshift.presentation.features.gig_detail

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.ui.theme.HintGray
import com.daffa.swiftshift.presentation.ui.theme.IconSizeMedium
import com.daffa.swiftshift.presentation.ui.theme.Primary700
import com.daffa.swiftshift.presentation.ui.theme.Slate300
import com.daffa.swiftshift.presentation.ui.theme.Slate600
import com.daffa.swiftshift.presentation.ui.theme.SpaceLarge
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.ui.theme.SpaceSmall
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.daffa.swiftshift.presentation.util.ObserveAsEvents
import com.daffa.swiftshift.util.DateUtil
import com.daffa.swiftshift.util.asString
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GigDetailScreen(
    navController: NavController,
    gigId: String,
    scaffoldState: ScaffoldState,
    viewModel: GigDetailViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.fetch(gigId)
    }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state by viewModel.state
    val gig = state.data

    ObserveAsEvents(flow = viewModel.eventFlow) { event ->
        when (event) {
            is GigDetailViewModel.UiEvent.ShowSnackBar -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context),
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SpaceMedium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(IconSizeMedium)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = stringResource(id = R.string.back_button),
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(SpaceMedium))
            Text(
                text = stringResource(R.string.gig_detail),
                style = Type.heading4SemiBold(),
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(SpaceMedium))

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            gig?.let {
                Text(
                    text = it.title,
                    style = Type.heading5SemiBold(),
                    color = Color.Black
                )
            } ?: kotlin.run {
                Box(
                    modifier = Modifier
                        .height(SpaceLarge * 2)
                        .width(SpaceLarge * 6)
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
            Text(
                text = "ALAMAT",
                style = Type.body2Light(),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(SpaceSmall))
            Card(
                colors = CardDefaults.cardColors(containerColor = Primary700),
            ) {
                gig?.let {
                    Text(
                        text = it.tag,
                        style = Type.body5Bold(),
                        color = Color.White,
                        modifier = Modifier.padding(4.dp)
                    )
                } ?: run {
                    Box(
                        modifier = Modifier
                            .height(SpaceLarge)
                            .width(SpaceLarge * 6)
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
            Spacer(modifier = Modifier.height(SpaceSmall))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SpaceLarge),
                elevation = CardDefaults.elevatedCardElevation(SpaceSmall)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(SpaceSmall),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.salary),
                            style = Type.body4Regular()
                        )
                        gig?.let {
                            Text(
                                text = "Rp. ${gig.salary},-",
                                style = Type.body3SemiBold()
                            )
                        } ?: kotlin.run {
                            Box(
                                modifier = Modifier
                                    .height(SpaceLarge)
                                    .width(SpaceLarge * 6)
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
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.date),
                            style = Type.body4Regular()
                        )
                        gig?.let {
                            val date = DateUtil.convertMillisecondToDateFormat(it.timestamp)
                            Text(
                                text = date,
                                style = Type.body3SemiBold()
                            )
                        } ?: kotlin.run {
                            Box(
                                modifier = Modifier
                                    .height(SpaceLarge)
                                    .width(SpaceLarge * 6)
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

            Spacer(modifier = Modifier.height(SpaceSmall))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SpaceLarge),
                elevation = CardDefaults.elevatedCardElevation(SpaceSmall)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(SpaceSmall),
                    verticalArrangement = Arrangement.spacedBy(SpaceSmall)
                ) {
                    Text(
                        text = stringResource(R.string.registrant_quota),
                        style = Type.body4SemiBold()
                    )
                    gig?.let {
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth(),
                            progress = it.currentApplier.toFloat() / it.maxApplier.toFloat(),
                            color = Primary700,
                            trackColor = Slate300
                        )
                    } ?: kotlin.run {
                        Box(
                            modifier = Modifier
                                .height(SpaceLarge)
                                .fillMaxWidth()
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
                    gig?.let {
                        Text(
                            text = "Free ${gig.maxApplier - gig.currentApplier} Slot",
                            style = Type.body5Light()
                        )
                    } ?: kotlin.run {
                        Box(
                            modifier = Modifier
                                .height(SpaceLarge)
                                .width(SpaceLarge * 6)
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
            Spacer(modifier = Modifier.height(SpaceLarge))

            val pagerState = rememberPagerState { 2 }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.description),
                    style = if (pagerState.currentPage == 0) Type.body3Medium() else Type.body3Light(),
                    color = if (pagerState.currentPage == 0) Primary700 else Slate600,
                    modifier = Modifier.clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    }
                )
                Spacer(modifier = Modifier.width(SpaceSmall))
                Text(
                    text = stringResource(R.string.location),
                    style = if (pagerState.currentPage == 1) Type.body3Medium() else Type.body3Light(),
                    color = if (pagerState.currentPage == 1) Primary700 else Slate600,
                    modifier = Modifier.clickable {
                        scope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    }
                )
            }
            Divider(color = Primary700)
            Spacer(modifier = Modifier.height(SpaceMedium))
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    when (pagerState.currentPage) {
                        0 -> {
                            gig?.let {
                                Text(
                                    text = gig.description,
                                    style = Type.body5Regular(),
                                    color = Color.Black,
                                    textAlign = TextAlign.Justify,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.height(SpaceLarge))
                            } ?: kotlin.run {
                                Box(
                                    modifier = Modifier
                                        .height(SpaceLarge)
                                        .width(SpaceLarge * 6)
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

                        1 -> {
                            gig?.let {
                                Text(
                                    text = gig.latitude.toString(),
                                    style = Type.body5Regular(),
                                    color = Color.Black,
                                )
                                Text(
                                    text = gig.longitude.toString(),
                                    style = Type.body5Regular(),
                                    color = Color.Black,
                                )
                            } ?: kotlin.run {
                                Box(
                                    modifier = Modifier
                                        .height(SpaceLarge)
                                        .width(SpaceLarge * 6)
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
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(SpaceLarge)
    ) {
        Button(
            onClick = {
                Toast.makeText(context, "Apply", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = stringResource(R.string.apply_for_gig),
                style = Type.heading4Medium(),
                color = Color.White
            )
        }
    }
}