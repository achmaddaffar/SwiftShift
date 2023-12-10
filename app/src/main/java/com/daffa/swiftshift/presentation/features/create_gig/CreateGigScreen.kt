package com.daffa.swiftshift.presentation.features.create_gig

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.component.DatePickerDialog
import com.daffa.swiftshift.presentation.component.SwiftShiftTextField
import com.daffa.swiftshift.presentation.ui.theme.IconSizeMedium
import com.daffa.swiftshift.presentation.ui.theme.Primary200
import com.daffa.swiftshift.presentation.ui.theme.Primary600
import com.daffa.swiftshift.presentation.ui.theme.Primary700
import com.daffa.swiftshift.presentation.ui.theme.SpaceLarge
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.ui.theme.SpaceSmall
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.daffa.swiftshift.presentation.util.ObserveAsEvents
import com.daffa.swiftshift.presentation.util.contract.CropActivityResultContract
import com.daffa.swiftshift.util.Constants
import com.daffa.swiftshift.util.DateUtil
import com.daffa.swiftshift.util.asString
import kotlinx.coroutines.launch

@Composable
fun CreateGigScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    latitude: Double,
    longitude: Double,
    viewModel: CreateGigViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val titleState by viewModel.titleState
    val imageState by viewModel.imageState
    val descriptionState by viewModel.descriptionState
    val tagState by viewModel.tagState
    val maxApplierState by viewModel.maxApplierState
    val salaryState by viewModel.salaryState
    val dueDateState by viewModel.dateState
    val isLoading by viewModel.isLoading

    var showDatePickerDialog by remember {
        mutableStateOf(false)
    }

    if (showDatePickerDialog)
        DatePickerDialog(
            onDismiss = {
                showDatePickerDialog = false
            },
            onConfirm = {
                it?.let {
                    viewModel.onEvent(CreateGigEvent.EnterDueDate(it))
                }
            }
        )

    if (isLoading)
        Dialog(onDismissRequest = {}) {
            CircularProgressIndicator(
                color = Primary600,
                modifier = Modifier.size(100.dp)
            )
        }

    val cropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(16f, 9f)
    ) {
        viewModel.onEvent(CreateGigEvent.CropImage(it))
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        it?.let {
            viewModel.onEvent(CreateGigEvent.PickImage(it))
            cropActivityLauncher.launch(it)
        }
    }

    ObserveAsEvents(flow = viewModel.eventFlow) { event ->
        when (event) {
            is CreateGigViewModel.UiEvent.ShowSnackBar -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context),
                        duration = SnackbarDuration.Long
                    )
                }
            }

            CreateGigViewModel.UiEvent.NavigateBack -> {
                navController.popBackStack()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = SpaceMedium
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = SpaceSmall),
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(SpaceSmall)
        ) {
            Spacer(modifier = Modifier.height(SpaceMedium))
            Box(
                modifier = Modifier
                    .aspectRatio(16f / 9f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(SpaceMedium))
                    .background(Primary200)
                    .clickable {
                        galleryLauncher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.btn_gallery),
                    contentDescription = stringResource(id = R.string.gallery),
                    modifier = Modifier.size(50.dp)
                )
                imageState?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = stringResource(id = R.string.profile_picture),
                        modifier = Modifier
                            .matchParentSize()
                            .clickable {
                                galleryLauncher.launch("image/*")
                            }
                    )
                }
            }
            AnimatedVisibility(visible = imageState != null) {
                Text(
                    text = stringResource(R.string.remove_photo),
                    style = Type.body5Bold(),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.clickable {
                        viewModel.onEvent(CreateGigEvent.RemoveImage)
                    }
                )
            }

            Spacer(modifier = Modifier.height(SpaceMedium))

            SwiftShiftTextField(
                text = titleState.text,
                hint = stringResource(R.string.enter_gig_title),
                label = stringResource(R.string.gig_title),
                onValueChange = {
                    viewModel.onEvent(CreateGigEvent.EnterGigTitle(it))
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
                text = descriptionState.text,
                hint = stringResource(R.string.enter_description),
                label = stringResource(R.string.description),
                maxLines = 5,
                maxLength = Constants.MAX_GIG_DESCRIPTION_LENGTH,
                onValueChange = {
                    viewModel.onEvent(CreateGigEvent.EnterDescription(it))
                }
            )

            SwiftShiftTextField(
                text = maxApplierState.text,
                hint = stringResource(R.string.enter_max_applier),
                label = stringResource(R.string.max_applier),
                onValueChange = {
                    viewModel.onEvent(CreateGigEvent.EnterMaxApplier(it))
                },
                keyboardType = KeyboardType.Decimal
            )

            SwiftShiftTextField(
                text = salaryState.text,
                hint = stringResource(R.string.enter_salary),
                label = stringResource(R.string.salary),
                onValueChange = {
                    viewModel.onEvent(CreateGigEvent.EnterSalary(it))
                },
                keyboardType = KeyboardType.Decimal
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SwiftShiftTextField(
                    text = dueDateState.text,
                    hint = DateUtil.convertMillisecondToDateFormat(System.currentTimeMillis()),
                    label = stringResource(R.string.due_date),
                    onValueChange = {

                    },
                    readonly = true,
                    modifier = Modifier.weight(0.8f)
                )
                IconButton(
                    onClick = {
                        showDatePickerDialog = true
                    },
                    modifier = Modifier
                        .weight(0.2f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_callendar),
                        contentDescription = stringResource(R.string.fill_due_date),
                        tint = Primary700,
                        modifier = Modifier
                            .size(75.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(SpaceLarge))
            Button(
                onClick = {
                    viewModel.onEvent(
                        CreateGigEvent.Post(
                            lat = latitude,
                            lon = longitude
                        )
                    )
                },
                enabled = viewModel.isButtonEnabled(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.post_gig),
                    style = Type.heading4Medium()
                )
            }
            Spacer(modifier = Modifier.height(SpaceLarge))
        }
    }
}