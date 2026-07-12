package com.crewcloud.apps.crewchat.presentation.ui.home.chatting

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.crewcloud.apps.crewchat.domain.model.Chatting
import com.crewcloud.apps.crewchat.presentation.core.component.DazoneImage
import com.crewcloud.apps.crewchat.presentation.core.theme.ChatListBgColor
import com.crewcloud.apps.crewchat.presentation.core.theme.ColorPrimary
import com.crewcloud.apps.crewchat.presentation.core.theme.ColorWhite
import com.crewcloud.apps.crewchat.presentation.core.theme.DivideColor
import com.crewcloud.apps.crewchat.presentation.core.theme.SecondaryTextColor
import com.crewcloud.apps.crewchat.presentation.core.theme.TimeColorCurrentChat

/**
 * Created by BM Anderson on 12/7/26.
 */
@Composable
fun ChattingScreen() {
    val viewModel: ChattingViewModel = hiltViewModel()
    val chatRows = viewModel.chatRows.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    LaunchedEffect(chatRows.itemCount) {
        if (chatRows.itemCount > 0) {
            listState.scrollToItem(chatRows.itemCount - 1)
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = ColorWhite)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.ArrowBackIos,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {

                        }
                )
                Text("CrewChat", modifier = Modifier.weight(1f))
            }

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(ChatListBgColor)
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    count = chatRows.itemCount,
                    key = { index -> chatRows[index]?.key ?: "placeholder_$index" }
                ) { index ->
                    val row = chatRows[index] ?: return@items
                    when (row) {
                        is ChatRow.DateHeader -> ChatDateHeader(text = row.text)
                        is ChatRow.Message -> ChatMessageRow(
                            message = row.message,
                            isMine = row.isMine
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChatDateHeader(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = ColorWhite,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(14.dp))
                .background(Color.Black.copy(alpha = 0.18f))
                .padding(horizontal = 12.dp, vertical = 5.dp)
        )
    }
}

@Composable
private fun ChatMessageRow(
    message: Chatting,
    isMine: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!isMine) {
            ChatMessageAvatar()
        }

        Column(
            horizontalAlignment = if (isMine) Alignment.End else Alignment.Start,
            modifier = Modifier.padding(horizontal = 6.dp)
        ) {
            if (!isMine) {
                Text(
                    text = message.senderName(),
                    fontSize = 11.sp,
                    color = SecondaryTextColor,
                    modifier = Modifier.padding(start = 4.dp, bottom = 3.dp)
                )
            }

            Text(
                text = message.message.ifBlank { message.attachFileName.ifBlank { " " } },
                fontSize = 14.sp,
                color = if (isMine) ColorWhite else Color.Black,
                modifier = Modifier
                    .widthIn(max = 260.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (isMine) 16.dp else 4.dp,
                            bottomEnd = if (isMine) 4.dp else 16.dp
                        )
                    )
                    .background(if (isMine) ColorPrimary else ColorWhite)
                    .then(
                        if (isMine) {
                            Modifier
                        } else {
                            Modifier.border(
                                width = 0.6.dp,
                                color = DivideColor.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(
                                    topStart = 16.dp,
                                    topEnd = 16.dp,
                                    bottomStart = 4.dp,
                                    bottomEnd = 16.dp
                                )
                            )
                        }
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )

            Text(
                text = message.displayTime(),
                fontSize = 10.sp,
                color = TimeColorCurrentChat,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 3.dp)
            )
        }
    }
}

@Composable
private fun ChatMessageAvatar() {
    Box(modifier = Modifier.size(34.dp)) {
        DazoneImage(
            imageUrl = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .align(Alignment.Center)
        )
    }
}
