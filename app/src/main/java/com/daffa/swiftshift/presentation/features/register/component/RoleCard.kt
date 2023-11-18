package com.daffa.swiftshift.presentation.features.register.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.daffa.swiftshift.presentation.ui.theme.Primary500
import com.daffa.swiftshift.presentation.ui.theme.SpaceSmall
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.daffa.swiftshift.presentation.util.state.SelectionOption

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
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    onOptionClicked(selectionOption)
                }
                .size(150.dp)
                .padding(SpaceSmall),
            colors = CardDefaults.elevatedCardColors(
                containerColor = if (selectionOption.selected) Primary500 else Color.Transparent
            )
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = stringResource(id = contentDescriptionRes),
                modifier = Modifier.padding(SpaceSmall)
            )
        }
        Text(
            text = title,
            style = Type.body3Bold()
        )
    }
}