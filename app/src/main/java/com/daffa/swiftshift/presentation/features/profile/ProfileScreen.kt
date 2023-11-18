package com.daffa.swiftshift.presentation.features.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Text
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.ui.theme.Type

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    // Edit
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 15.dp),
    ) {
        Text(
            text = "Edit",
            color = Color.Black,
            modifier = Modifier.padding(all = 5.dp),
            style = Type.body2Regular(),
        )
    }
    // Add padding around our message
    Column(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(),

        ) {
        //Image Profile
        Row(
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.goceng),
                contentDescription = "Contact profile picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    // Set image size to 147 dp
                    .size(147.dp)
                    // Clip image to be shaped as a circle
                    .clip(CircleShape)
            )

            // Add a horizontal space between the image and the column
            Spacer(modifier = Modifier.height(10.dp))

        }
        //Name Profile
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "John Doe",
                color = Color.Black,
                modifier = Modifier.padding(all = 5.dp),
                style = Type.heading4SemiBold(),
                textAlign = TextAlign.Center
            )
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
                    text = "Total Income",
                    color = Color.Black,
                    modifier = Modifier.padding(all = 5.dp),
                    style = Type.body4Regular(),
                    textAlign = TextAlign.Center

                )
                Text(
                    text = "Rp. 150.000,-",
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
                    text = "Joining Date",
                    color = Color.Black,
                    modifier = Modifier.padding(all = 5.dp),
                    style = Type.body4Regular(),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "25 December 2022",
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
                text = "Bio Data",
                color = Color.Black,
                modifier = Modifier.padding(all = 5.dp),
                style = Type.heading5SemiBold(),
            )

        }
        //Email
        Row {
            Text(
                text = "Email",
                color = Color.Black,
                modifier = Modifier.padding(all = 3.dp),
                style = Type.body3Bold(),
            )
        }
        // Email Field
        Row {
            Text(
                text = "john.doe@example.com",
                color = Color.Black,
                modifier = Modifier.padding(all = 3.dp),
                style = Type.body3Regular(),
            )
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
                text = "01/01/1990",
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
            Text(
                text = "Male",
                color = Color.Black,
                modifier = Modifier.padding(all = 3.dp),
                style = Type.body3Regular(),
            )
        }
        // Highest Education
        Row {
            Text(
                text = "Highest Education",
                color = Color.Black,
                modifier = Modifier.padding(all = 3.dp),
                style = Type.body3Bold(),
            )
        }
        // Highest Education
        Row {
            Text(
                text = "Bachelor’s Degree",
                color = Color.Black,
                modifier = Modifier.padding(all = 3.dp),
                style = Type.body3Regular(),
            )
        }
        // Curriculum Vitae
        Row {
            Text(
                text = "Curriculum Vitae",
                color = Color.Black,
                modifier = Modifier.padding(all = 5.dp),
                style = Type.heading5SemiBold(),
            )
        }

    }
}

