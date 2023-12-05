package com.daffa.swiftshift.presentation.features.nearby_gigs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.component.BaseCardShimmer
import com.daffa.swiftshift.presentation.features.home.component.NearbyGigCard
import com.daffa.swiftshift.presentation.ui.theme.IconSizeMedium
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.daffa.swiftshift.presentation.util.ObserveAsEvents
import com.daffa.swiftshift.util.asString
import kotlinx.coroutines.launch

@Composable
fun NearbyGigsScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    latitude: Double,
    longitude: Double,
    viewModel: NearbyGigsViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state by viewModel.state

    viewModel.fetch(
        latitude = latitude,
        longitude = longitude
    )

    ObserveAsEvents(flow = viewModel.eventFlow) { event ->
        when (event) {
            is NearbyGigsViewModel.UiEvent.ShowSnackBar -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context)
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = SpaceMedium,
                top = SpaceMedium,
                end = SpaceMedium
            )
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
                text = stringResource(id = R.string.nearby_gigs),
                style = Type.heading4SemiBold(),
                color = Color.Black
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            if (state.isLoading)
                items(10) {
                    BaseCardShimmer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = SpaceMedium)
                            .height(225.dp)
                    )
                    Spacer(modifier = Modifier.height(SpaceMedium))
                }
            else {
                val gigs = viewModel.state.value.gigs
                items(gigs.size) { index ->
                    gigs[index]?.let {
                        NearbyGigCard(
                            gig = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(225.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(SpaceMedium))
                }
            }
        }
    }
}