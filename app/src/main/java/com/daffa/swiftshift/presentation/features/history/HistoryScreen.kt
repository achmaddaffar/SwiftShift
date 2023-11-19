package com.daffa.swiftshift.presentation.features.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.ui.theme.Type

@Composable
fun HistoryScreen(
    navController: NavController,
    viewModel: HistoryViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SpaceMedium)
    ) {
        Text(
            text = stringResource(id = R.string.history),
            style = Type.heading4SemiBold(),
            modifier = Modifier.fillMaxWidth()
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

        }
    }
}