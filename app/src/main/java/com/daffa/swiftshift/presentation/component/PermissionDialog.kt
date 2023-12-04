package com.daffa.swiftshift.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.daffa.swiftshift.R
import com.daffa.swiftshift.presentation.ui.theme.SpaceMedium
import com.daffa.swiftshift.presentation.ui.theme.Type
import com.daffa.swiftshift.util.asString

//@Composable
//fun PermissionDialog(
//    permissionTextProvider: PermissionTextProvider,
//    isPermanentlyDeclined: Boolean,
//    onDismiss: () -> Unit,
//    onOkClick: () -> Unit,
//    onGoToAppSettingsClick: () -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    AlertDialog(
//        onDismissRequest = onDismiss,
//        buttons = {
//            Column(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Divider()
//                Text(
//                    text = if (isPermanentlyDeclined) {
//                        stringResource(R.string.grant_permission)
//                    } else {
//                        stringResource(R.string.ok)
//                    },
//                    style = Type.body5Bold(),
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable {
//                            if (isPermanentlyDeclined) {
//                                onGoToAppSettingsClick()
//                            } else {
//                                onOkClick()
//                            }
//                        }
//                        .padding(SpaceMedium)
//                )
//            }
//        },
//        title = {
//            Text(text = stringResource(R.string.permission_required))
//        },
//        text = {
//            Text(
//                text = permissionTextProvider.getDescription(
//                    isPermanentlyDeclined
//                ).asString()
//            )
//        },
//        modifier = modifier
//    )
//}