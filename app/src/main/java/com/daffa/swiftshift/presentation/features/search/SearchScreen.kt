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
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.component.BaseCardShimmer
import com.daffa.swiftshift.presentation.features.home.component.NearbyGigCard
import com.daffa.swiftshift.presentation.features.search.component.SearchGigCard
import com.daffa.swiftshift.presentation.ui.theme.IconSizeMedium
import com.daffa.swiftshift.presentation.ui.theme.Slate800
import com.daffa.swiftshift.presentation.ui.theme.SpaceLarge
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.daffa.swiftshift.presentation.util.ObserveAsEvents
import com.daffa.swiftshift.util.asString
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val query by viewModel.query
    val state by viewModel.state

    ObserveAsEvents(flow = viewModel.eventFlow) { event ->
        when (event) {
            is SearchViewModel.UiEvent.ShowSnackBar -> {
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
        OutlinedTextField(
            value = query,
            onValueChange = {
                viewModel.onEvent(SearchEvent.EnteredSearchQuery(it))
            },
            modifier = Modifier.fillMaxWidth(),
            textStyle = Type.body5Regular(),
            singleLine = true,
            shape = CircleShape,
            label = {
                Text(
                    text = stringResource(R.string.search_gigs),
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
            else
                items(state.gigItems.size) { index ->
                    SearchGigCard(
                        gig = state.gigItems[index],
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(225.dp)
                            .padding(horizontal = SpaceMedium)
                    )
                }
        }
    }
}