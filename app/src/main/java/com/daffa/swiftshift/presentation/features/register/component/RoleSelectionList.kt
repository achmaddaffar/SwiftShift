package com.daffa.swiftshift.presentation.features.register.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.util.state.SelectionOption

@Composable
fun RoleSelectionList(
    options: List<SelectionOption<String>>,
    onOptionClicked: (SelectionOption<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RoleCard(
            title = stringResource(R.string.gig_worker),
            imageRes = R.drawable.img_gig_worker,
            contentDescriptionRes = R.string.gig_worker_role,
            onOptionClicked = onOptionClicked,
            selectionOption = options[0]
        )
        Spacer(modifier = Modifier.width(SpaceMedium))
        RoleCard(
            title = stringResource(R.string.gig_provider),
            imageRes = R.drawable.img_gig_provider,
            contentDescriptionRes = R.string.gig_provider_role,
            onOptionClicked = onOptionClicked,
            selectionOption = options[1]
        )
    }
}