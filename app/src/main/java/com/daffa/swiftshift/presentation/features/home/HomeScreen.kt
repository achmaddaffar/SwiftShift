package com.daffa.swiftshift.presentation.features.home

import android.Manifest
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.component.RationaleAlert
import com.daffa.swiftshift.presentation.features.home.component.AdCard
import com.daffa.swiftshift.presentation.features.home.component.HomeTopBanner
import com.daffa.swiftshift.presentation.features.home.component.NearbyGigCard
import com.daffa.swiftshift.presentation.features.home.component.NearbyGigCardShimmer
import com.daffa.swiftshift.presentation.features.home.component.RecommendedGigCard
import com.daffa.swiftshift.presentation.features.home.component.RecommendedGigCardShimmer
import com.daffa.swiftshift.presentation.navigation.Screen
import com.daffa.swiftshift.presentation.ui.theme.Primary600
import com.daffa.swiftshift.presentation.ui.theme.Slate600
import com.daffa.swiftshift.presentation.ui.theme.SpaceLarge
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.ui.theme.SpaceSmall
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.daffa.swiftshift.presentation.util.NavArguments
import com.daffa.swiftshift.presentation.util.permission.PermissionEvent
import com.daffa.swiftshift.util.Resource
import com.daffa.swiftshift.util.hasLocationPermission
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.LatLng

@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun HomeScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val gigWorker by viewModel.gigWorker
    val recommendedGigs by viewModel.recommendedGigs
    val nearbyGigsPreview by viewModel.nearbyGigsPreview
    val state by viewModel.state.collectAsStateWithLifecycle()

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(!context.hasLocationPermission()) {
        permissionState.launchMultiplePermissionRequest()
    }

    when {
        permissionState.allPermissionsGranted -> {
            LaunchedEffect(Unit) {
                viewModel.handle(PermissionEvent.Granted)
            }
        }

        permissionState.shouldShowRationale -> {
            RationaleAlert(
                onDismiss = { }
            ) {
                permissionState.launchMultiplePermissionRequest()
            }
        }

        !permissionState.allPermissionsGranted && !permissionState.shouldShowRationale -> {
            LaunchedEffect(Unit) {
                viewModel.handle(PermissionEvent.Revoked)
            }
        }
    }

    when (state) {
        is Resource.Error<*> -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(SpaceLarge),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.we_need_permissions_to_use_this_app),
                    style = Type.heading5SemiBold()
                )
                Button(
                    onClick = {
                        context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                    }
                ) {
                    if (context.hasLocationPermission()) CircularProgressIndicator(
                        modifier = Modifier.size(SpaceMedium),
                        color = Color.White
                    )
                    else Text(
                        text = stringResource(R.string.settings),
                        style = Type.body5Bold()
                    )
                }
            }
        }

        is Resource.Loading<*> -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success<*> -> {
            val currentLoc = LatLng(
                state.data?.latitude ?: 0.0,
                state.data?.longitude ?: 0.0
            )
            println("latitude: ${currentLoc.latitude}")
            println("longitude: ${currentLoc.longitude}")

            viewModel.getProfile()
            viewModel.getRecommendedGigs(currentLoc)
            viewModel.getNearbyGigsPreview(currentLoc)

            val adList = listOf(null, "")

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                item {
                    HomeTopBanner(
                        userFullName = gigWorker?.fullName,
                        profilePictureUrl = viewModel.gigWorker.value?.profileImageUrl,
                        locationName = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(SpaceMedium)
                    )
                    Spacer(modifier = Modifier.height(SpaceLarge))
                }
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(adList.size) { index ->
                            Spacer(modifier = Modifier.width(SpaceMedium))
                            AdCard(
                                adImageUrl = adList[index],
                                modifier = Modifier
                                    .height(150.dp)
                                    .width(280.dp)
                            )
                            if (index == adList.lastIndex)
                                Spacer(modifier = Modifier.width(SpaceMedium))
                        }
                    }
                    Spacer(modifier = Modifier.height(SpaceLarge))
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = SpaceMedium
                            )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.recommended_gigs),
                                style = Type.body2Bold(),
                                color = Color.Black
                            )
                            Text(
                                text = stringResource(R.string.see_all),
                                style = Type.body5Regular(),
                                color = Slate600,
                                modifier = Modifier.clickable {

                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(SpaceSmall))
                        if (recommendedGigs.isNotEmpty())
                            RecommendedGigCard(
                                gig = recommendedGigs[0],
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(225.dp)
                            )
                        else
                            RecommendedGigCardShimmer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(225.dp)
                            )
                    }
                    Spacer(modifier = Modifier.height(SpaceLarge))
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = SpaceMedium
                            )
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.nearby_gigs),
                                style = Type.body2Bold(),
                                color = Color.Black
                            )
                            Text(
                                text = stringResource(id = R.string.see_all),
                                style = Type.body5Regular(),
                                color = Slate600,
                                modifier = Modifier.clickable {
                                    navController.navigate(
                                        Screen.NearbyGigsScreen.withArgs(
                                            currentLoc.latitude.toString(),
                                            currentLoc.longitude.toString()
                                        )
                                    )
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(SpaceSmall))
                }
                if (nearbyGigsPreview.isEmpty()) {
                    items(5) {
                        NearbyGigCardShimmer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = SpaceMedium)
                                .height(225.dp)
                        )
                        Spacer(modifier = Modifier.height(SpaceMedium))
                    }
                } else {
                    items(nearbyGigsPreview.size) { index ->
                        NearbyGigCard(
                            gig = nearbyGigsPreview[index],
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = SpaceMedium)
                                .height(225.dp)
                        )
                        Spacer(modifier = Modifier.height(SpaceMedium))
                    }
                }
            }
        }
    }
}