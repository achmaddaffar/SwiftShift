package com.daffa.swiftshift.presentation.features.login.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.daffa.swiftshift.presentation.ui.theme.Primary500
import com.daffa.swiftshift.presentation.ui.theme.SpaceSmall
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.daffa.swiftshift.presentation.util.state.SelectionOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleCard(
    title: String,
    @DrawableRes imageRes: Int,
    @StringRes contentDescriptionRes: Int,
    selectionOption: SelectionOption<String>,
    onOptionClicked: (SelectionOption<String>) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = modifier
                .padding(SpaceSmall)
                .size(150.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 12.dp
            ),
            onClick = {
                onOptionClicked(selectionOption)
            }
        ) {
            Surface(
                color = if (selectionOption.selected) Primary500 else Color.White,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = stringResource(id = contentDescriptionRes),
                    modifier = Modifier.padding(SpaceSmall)
                )
            }
        }
        Text(
            text = title,
            style = Type.body3Bold()
        )
    }
}