package com.daffa.swiftshift.presentation.features.create_gig

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.component.SwiftShiftTextField
import com.daffa.swiftshift.presentation.features.register.RegisterEvent
import com.daffa.swiftshift.presentation.ui.theme.IconSizeMedium
import com.daffa.swiftshift.presentation.ui.theme.SpaceLarge
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.ui.theme.SpaceSmall
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.daffa.swiftshift.presentation.util.contract.CropActivityResultContract

@Composable
fun CreateGigScreen(
    navController: NavController,
    viewModel: CreateGigViewModel = hiltViewModel(),
) {

    val titleState by viewModel.titleState
    val imageState by viewModel.imageState
    val tagState by viewModel.tagState
    val maxApplierState by viewModel.maxApplierState
    val salaryState by viewModel.salaryState
    val dueDateState by viewModel.dateState

    val cropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(1f, 1f)
    ) {
        viewModel.onEvent(CreateGigEvent.CropImage(it))
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        cropActivityLauncher.launch(it)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(SpaceMedium),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(SpaceSmall)
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
                    text = stringResource(R.string.post_a_gig),
                    style = Type.heading4SemiBold(),
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(SpaceLarge))

            SwiftShiftTextField(
                text = titleState.text,
                hint = stringResource(R.string.enter_gig_title),
                label = stringResource(R.string.gig_title),
                onValueChange = {
                    viewModel.onEvent(CreateGigEvent.EnterGigTitle(it))
                }
            )

            SwiftShiftTextField(
                text = stringResource(R.string.image),
                hint = stringResource(R.string.insert_an_image),
                label = stringResource(R.string.gig_title),
                onValueChange = {

                }
            )

            SwiftShiftTextField(
                text = tagState.text,
                hint = stringResource(R.string.enter_tag),
                label = stringResource(R.string.tags),
                onValueChange = {
                    viewModel.onEvent(CreateGigEvent.EnterTag(it))
                }
            )

            SwiftShiftTextField(
                text = maxApplierState.text,
                hint = stringResource(R.string.enter_max_applier),
                label = stringResource(R.string.max_applier),
                onValueChange = {
                    viewModel.onEvent(CreateGigEvent.EnterMaxApplier(it.toInt()))
                },
                keyboardType = KeyboardType.Decimal
            )
        }
        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.post_gig),
                style = Type.heading4Medium()
            )
        }
    }
}