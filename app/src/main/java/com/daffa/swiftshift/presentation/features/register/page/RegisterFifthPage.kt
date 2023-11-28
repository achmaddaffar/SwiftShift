package com.daffa.swiftshift.presentation.features.register.page

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.features.register.RegisterEvent
import com.daffa.swiftshift.presentation.features.register.RegisterViewModel
import com.daffa.swiftshift.presentation.navigation.Screen
import com.daffa.swiftshift.presentation.ui.theme.HintGray
import com.daffa.swiftshift.presentation.ui.theme.Primary200
import com.daffa.swiftshift.presentation.ui.theme.Primary600
import com.daffa.swiftshift.presentation.ui.theme.Primary700
import com.daffa.swiftshift.presentation.ui.theme.SpaceLarge
import com.daffa.swiftshift.presentation.ui.theme.SpaceSmall
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.daffa.swiftshift.presentation.util.contract.CropActivityResultContract
import com.daffa.swiftshift.presentation.util.ObserveAsEvents
import com.daffa.swiftshift.util.asString
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RegisterFifthPage(
    navController: NavController,
    pagerState: PagerState,
    scaffoldState: ScaffoldState,
    viewModel: RegisterViewModel,
) {
    val registerState by viewModel.registerState
    val imageUri by viewModel.chosenImageUri
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val cropActivityLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract(1f, 1f)
    ) {
        viewModel.onEvent(RegisterEvent.CropImage(it))
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        cropActivityLauncher.launch(it)
    }

    ObserveAsEvents(flow = viewModel.eventFlow) { event ->
        when (event) {
            RegisterViewModel.UiEvent.NavigateToLogin -> {
                Toast.makeText(
                    context,
                    R.string.success_register,
                    Toast.LENGTH_LONG
                ).show()
                navController.navigate(Screen.LoginScreen.route) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }

            is RegisterViewModel.UiEvent.ShowSnackBar -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context),
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

    if (registerState.isLoading)
        Dialog(
            onDismissRequest = {}
        ) {
            CircularProgressIndicator(
                color = Primary600,
                modifier = Modifier.size(100.dp)
            )
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(SpaceLarge)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.register_step, pagerState.currentPage + 1),
                style = Type.body2Bold(),
                color = Primary600
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.upload_your_photo),
                style = Type.heading4Bold()
            )
            Spacer(modifier = Modifier.height(SpaceSmall))
            Text(
                text = stringResource(R.string.fourth_register_description),
                style = Type.body4Regular()
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(250.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
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
                imageUri?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = stringResource(id = R.string.profile_picture),
                        modifier = Modifier.matchParentSize()
                    )
                }
            }
            Spacer(modifier = Modifier.height(SpaceSmall))
            AnimatedVisibility(visible = imageUri == null) {
                Text(
                    text = stringResource(R.string.tap_here_to_upload),
                    style = Type.body5Regular()
                )
            }
            AnimatedVisibility(visible = imageUri != null) {
                Text(
                    text = stringResource(R.string.remove_photo),
                    style = Type.body5Bold(),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.clickable {
                        viewModel.onEvent(RegisterEvent.RemovePhoto)
                    }
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            val policyText = buildAnnotatedString {
                append(stringResource(R.string.by_proceeding_you_agree_to_our))
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(stringResource(R.string.terms_of_service))
                }
                append(",")
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(stringResource(R.string.privacy_policy))
                }
                append(", ")
                append(stringResource(R.string.and))
                append(" ")
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(stringResource(R.string.cookie_policy))
                }
                append(".")
            }
            Text(
                text = policyText,
                style = Type.body5Regular(),
                textAlign = TextAlign.Justify,
                color = HintGray,
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(SpaceSmall))
            AnimatedVisibility(visible = imageUri == null) {
                OutlinedButton(
                    onClick = {
                        viewModel.onEvent(RegisterEvent.Register)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(bottom = SpaceLarge),
                    border = BorderStroke(2.dp, Primary700)
                ) {
                    Text(
                        text = stringResource(R.string.i_will_do_it_later),
                        style = Type.heading4SemiBold(),
                        color = Primary700
                    )
                }
            }
            AnimatedVisibility(visible = imageUri != null) {
                Button(
                    onClick = {
                        viewModel.onEvent(RegisterEvent.Register)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(bottom = SpaceLarge),
                ) {
                    Text(
                        text = stringResource(R.string.done),
                        style = Type.heading4SemiBold(),
                        color = Color.White
                    )
                }
            }
        }
    }
}