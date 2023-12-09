package com.daffa.swiftshift.presentation.features.profile

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.TextButton
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import coil.compose.SubcomposeAsyncImage
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.navigation.Screen
import com.daffa.swiftshift.presentation.ui.theme.CoralRed
import com.daffa.swiftshift.presentation.ui.theme.HintGray
import com.daffa.swiftshift.presentation.ui.theme.SpaceLarge
import com.daffa.swiftshift.presentation.ui.theme.SpaceSmall
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.daffa.swiftshift.presentation.util.ObserveAsEvents
import com.daffa.swiftshift.util.DateUtil
import com.daffa.swiftshift.util.Role
import com.daffa.swiftshift.util.asString
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val role = viewModel.getRole()
    val isLoading by viewModel.isLoading
    val gigWorker by viewModel.gigWorker
    val gigProvider by viewModel.gigProvider

    ObserveAsEvents(flow = viewModel.uiFlow) { event ->
        when (event) {
            is ProfileViewModel.UiEvent.ShowSnackBar -> {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText.asString(context),
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }

    // Edit
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 15.dp),
    ) {
        Text(
            text = stringResource(R.string.edit),
            color = Color.Black,
            modifier = Modifier.padding(SpaceSmall),
            style = Type.body2Regular(),
        )
    }

    // Add padding around our message
    Column(
        modifier = Modifier
            .padding(SpaceLarge)
            .fillMaxWidth()
    ) {
        //Image Profile
        Row(
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            if (role == Role.GigWorker)
                SubcomposeAsyncImage(
                    model = gigWorker?.profileImageUrl,
                    contentDescription = stringResource(id = R.string.profile_picture),
                    contentScale = ContentScale.Crop,
                    loading = { CircularProgressIndicator() },
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )
            else
                SubcomposeAsyncImage(
                    model = gigProvider?.profileImageUrl,
                    contentDescription = stringResource(id = R.string.profile_picture),
                    contentScale = ContentScale.Crop,
                    loading = { CircularProgressIndicator() },
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                )

            // Add a horizontal space between the image and the column
            Spacer(modifier = Modifier.height(SpaceSmall))

        }
        //Name Profile
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            if (isLoading)
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
            else {
                if (role == Role.GigWorker)
                    Text(
                        text = gigWorker?.fullName.toString(),
                        color = Color.Black,
                        modifier = Modifier.padding(all = 5.dp),
                        style = Type.heading4SemiBold(),
                        textAlign = TextAlign.Center
                    )
                else
                    Text(
                        text = gigProvider?.fullName.toString(),
                        color = Color.Black,
                        modifier = Modifier.padding(all = 5.dp),
                        style = Type.heading4SemiBold(),
                        textAlign = TextAlign.Center
                    )
            }
            // Add a vertical space between the author and message texts
            Spacer(modifier = Modifier.height(4.dp))
        }
        //Total and Joining Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 10.dp)
        ) {
            //Total Income
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.total_income),
                    color = Color.Black,
                    modifier = Modifier.padding(all = 5.dp),
                    style = Type.body4Regular(),
                    textAlign = TextAlign.Center

                )
                val totalIncome =
                    if (role == Role.GigWorker) gigWorker?.totalIncome else gigProvider?.totalIncome

                if (isLoading)
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
                else
                    Text(
                        text = "Rp. " + (totalIncome?.toString() ?: "-"),
                        color = Color.Black,
                        modifier = Modifier.padding(all = 5.dp),
                        style = Type.body3SemiBold(),
                        textAlign = TextAlign.Center
                    )
            }
            //Joining Date
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.joining_date),
                    color = Color.Black,
                    modifier = Modifier.padding(all = 5.dp),
                    style = Type.body4Regular(),
                    textAlign = TextAlign.Center
                )
                val date =
                    (if (role == Role.GigWorker) gigWorker?.joiningDate else gigProvider?.joiningDate)?.let {
                        DateUtil.convertMillisecondToDateFormat(it)
                    }
                Text(
                    text = date.toString(),
                    color = Color.Black,
                    modifier = Modifier.padding(all = 5.dp),
                    style = Type.body3SemiBold(),
                    textAlign = TextAlign.Center
                )
            }
        }
        //Bio Data
        Row {
            Text(
                text = stringResource(R.string.bio_data),
                color = Color.Black,
                modifier = Modifier.padding(all = 5.dp),
                style = Type.heading5SemiBold(),
            )
        }
        //Email
        Row {
            Text(
                text = stringResource(R.string.email),
                color = Color.Black,
                modifier = Modifier.padding(all = 3.dp),
                style = Type.body3Bold(),
            )
        }
        // Email Field
        Row {
            if (isLoading)
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
            else {
                if (role == Role.GigWorker)
                    Text(
                        text = gigWorker?.email.toString(),
                        color = Color.Black,
                        modifier = Modifier.padding(all = 3.dp),
                        style = Type.body3Regular(),
                    )
                else
                    Text(
                        text = gigProvider?.email.toString(),
                        color = Color.Black,
                        modifier = Modifier.padding(all = 3.dp),
                        style = Type.body3Regular(),
                    )
            }
        }
        // Birth Date
        Row {
            Text(
                text = "Birth Date",
                color = Color.Black,
                modifier = Modifier.padding(all = 3.dp),
                style = Type.body3Bold(),
            )
        }
        // BIrth Date Field
        Row {
            Text(
                text = "-",
                color = Color.Black,
                modifier = Modifier.padding(all = 3.dp),
                style = Type.body3Regular(),
            )
        }
        // Gender
        Row {
            Text(
                text = "Gender",
                color = Color.Black,
                modifier = Modifier.padding(all = 3.dp),
                style = Type.body3Bold(),
            )
        }
        // Gender Field
        Row {
            if (isLoading)
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
            else {
                if (role == Role.GigWorker)
                    Text(
                        text = gigWorker?.gender ?: "-",
                        color = Color.Black,
                        modifier = Modifier.padding(all = 3.dp),
                        style = Type.body3Regular(),
                    )
                else
                    Text(
                        text = gigProvider?.gender ?: "-",
                        color = Color.Black,
                        modifier = Modifier.padding(all = 3.dp),
                        style = Type.body3Regular(),
                    )
            }

        }
        // Highest Education
        Row {
            Text(
                text = stringResource(R.string.highest_education),
                color = Color.Black,
                modifier = Modifier.padding(all = 3.dp),
                style = Type.body3Bold(),
            )
        }
        // Highest Education
        Row {
            if (isLoading)
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
            else {
                if (role == Role.GigWorker)
                    Text(
                        text = gigWorker?.highestEducation ?: "-",
                        color = Color.Black,
                        modifier = Modifier.padding(all = 3.dp),
                        style = Type.body3Regular(),
                    )
                else
                    Text(
                        text = gigProvider?.highestEducation ?: "-",
                        color = Color.Black,
                        modifier = Modifier.padding(all = 3.dp),
                        style = Type.body3Regular(),
                    )
            }
        }
        // Curriculum Vitae
        Row {
            Text(
                text = stringResource(R.string.curriculum_vitae),
                color = Color.Black,
                modifier = Modifier.padding(all = 5.dp),
                style = Type.heading5SemiBold(),
            )
        }

        TextButton(
            onClick = {
                viewModel.logout()
                navController.navigate(Screen.SplashScreen.route) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        ) {
            Text(
                text = stringResource(R.string.logout),
                style = Type.body3Bold(),
                color = CoralRed
            )
        }
    }
}