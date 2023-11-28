package com.daffa.swiftshift.presentation.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.daffa.swiftshift.R
import com.daffa.swiftshift.domain.model.Gig
import com.daffa.swiftshift.presentation.features.home.component.AdCard
import com.daffa.swiftshift.presentation.features.home.component.HomeTopBanner
import com.daffa.swiftshift.presentation.features.home.component.NearbyGigCard
import com.daffa.swiftshift.presentation.features.home.component.RecommendedGigCard
import com.daffa.swiftshift.presentation.ui.theme.Slate600
import com.daffa.swiftshift.presentation.ui.theme.SpaceLarge
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.ui.theme.SpaceSmall
import com.daffa.swiftshift.presentation.ui.theme.Type

@Composable
fun HomeScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val adList = listOf(null, "")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            HomeTopBanner(
                userFullName = "Dimas Cimol Emmanuel",
                profilePictureUrl = null,
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
                RecommendedGigCard(
                    gig = Gig(
                        title = "Buy Monthly Groceries",
                        tag = "Delivery",
                        imageUrl = "https://d32ijn7u0aqfv4.cloudfront.net/wp/wp-content/uploads/raw/SORL0423010_1560x880_desktop.jpg",
                        gigProviderName = "John Family",
                        wage = 50000.0,
                        location = "Malang",
                        timestamp = 10
                    ),
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

                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(SpaceSmall))
        }
        val item = 5
        items(item) {
            NearbyGigCard(
                gig = Gig(
                    title = "Buy Monthly Groceries $it",
                    tag = "Delivery",
                    imageUrl = "https://d32ijn7u0aqfv4.cloudfront.net/wp/wp-content/uploads/raw/SORL0423010_1560x880_desktop.jpg",
                    gigProviderName = "John Family",
                    wage = 50000.0,
                    location = "Malang",
                    timestamp = 10
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(225.dp)
                    .padding(horizontal = SpaceMedium)
            )
            Spacer(modifier = Modifier.height(SpaceMedium))
        }
    }
}
