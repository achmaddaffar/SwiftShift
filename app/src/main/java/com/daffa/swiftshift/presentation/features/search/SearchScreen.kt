package com.daffa.swiftshift.presentation.features.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.daffa.swiftshift.R
import com.daffa.swiftshift.domain.model.Gig
import com.daffa.swiftshift.presentation.features.home.component.NearbyGigCard
import com.daffa.swiftshift.presentation.ui.theme.HintGray
import com.daffa.swiftshift.presentation.ui.theme.IconSizeMedium
import com.daffa.swiftshift.presentation.ui.theme.Slate800
import com.daffa.swiftshift.presentation.ui.theme.SpaceLarge
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.ui.theme.Type

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val queryState by viewModel.searchState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = SpaceMedium,
                top = SpaceMedium,
                end = SpaceMedium
            )
    ) {
        OutlinedTextField(
            value = queryState.text,
            onValueChange = {
                viewModel.onEvent(SearchEvent.EnteredSearchQuery(it))
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = Type.body5Regular(),
            singleLine = true,
            shape = CircleShape,
            label = {
                Text(
                    text = "Search Gigs",
                    style = Type.body5Regular()
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.icon_search),
                    contentDescription = stringResource(R.string.search_icon),
                    modifier = Modifier.size(IconSizeMedium),
                    tint = Slate800
                )
            }
        )
        Spacer(modifier = Modifier.height(SpaceLarge))
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
//                NearbyGigCard(
//                    gig = Gig(
//                        title = "Buy Monthly Groceries",
//                        tag = "Delivery",
//                        imageUrl = "https://d32ijn7u0aqfv4.cloudfront.net/wp/wp-content/uploads/raw/SORL0423010_1560x880_desktop.jpg",
//                        gigProviderName = "John Family",
//                        wage = 50000.0,
//                        location = "Malang",
//                        timestamp = 10
//                    ),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(225.dp)
//                        .padding(horizontal = SpaceMedium)
//                )
            }
        }
    }
}