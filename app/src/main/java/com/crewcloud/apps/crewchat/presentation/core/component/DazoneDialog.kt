package com.crewcloud.apps.crewchat.presentation.core.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.crewcloud.apps.crewchat.R
import com.crewcloud.apps.crewchat.presentation.core.theme.ColorHint
import com.crewcloud.apps.crewchat.presentation.core.theme.ColorPrimary

/**
 * Created by BM Anderson on 3/7/26.
 */
@Composable
fun DazoneDialog(
    title: String?= null,
    content: String,
    positiveStr: String,
    negativeStr: String?= null,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
) {
    val titleDialog = title ?: stringResource(R.string.app_name)

    Dialog(
        onDismissRequest = onNegativeClick,
        properties = DialogProperties(
            dismissOnBackPress = false,    // Bấm nút Back hệ thống không tắt
            dismissOnClickOutside = false  // Bấm ra ngoài vùng Dialog không tắt
        )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Column(modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp)) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(12.dp).fillMaxWidth()) {
                    Text(
                        titleDialog, style = TextStyle(
                            color = ColorPrimary
                        )
                    )
                }


                HorizontalDivider(
                    color = ColorHint,
                    modifier = Modifier.height(1.dp)
                )

                Box(modifier = Modifier.padding(12.dp).defaultMinSize(minHeight = 60.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(
                        content, style = TextStyle(
                            color = ColorPrimary
                        )
                    )
                }

                HorizontalDivider(
                    color = ColorHint,
                    modifier = Modifier.height(1.dp)
                )

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth().height(
                        IntrinsicSize.Min)) {
                        TextButton(onClick = onPositiveClick) {
                            Text(positiveStr)
                        }

                        if (negativeStr != null) {
                            VerticalDivider(modifier = Modifier.width(1.dp), color = ColorHint)
                            TextButton(onClick = onNegativeClick) {
                                Text(negativeStr)
                            }
                        }
                    }
                }
            }
        }
    }
}