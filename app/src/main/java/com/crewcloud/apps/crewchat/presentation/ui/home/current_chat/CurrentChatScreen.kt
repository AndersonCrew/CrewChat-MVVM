package com.crewcloud.apps.crewchat.presentation.ui.home.current_chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Addchart
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.crewcloud.apps.crewchat.domain.model.CurrentChat
import com.crewcloud.apps.crewchat.presentation.core.component.DazoneImage
import com.crewcloud.apps.crewchat.presentation.core.theme.BadgeBgColor
import com.crewcloud.apps.crewchat.presentation.core.theme.ColorPrimary
import com.crewcloud.apps.crewchat.presentation.core.theme.ColorWhite
import com.crewcloud.apps.crewchat.presentation.core.theme.DivideColor
import com.crewcloud.apps.crewchat.presentation.core.theme.SecondaryTextColor
import com.crewcloud.apps.crewchat.presentation.core.theme.TextViewTimeBg
import com.crewcloud.apps.crewchat.presentation.core.theme.TimeColorCurrentChat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by BM Anderson on 12/7/26.
 */
@Composable
fun CurrentChatScreen(
    onChatRoomClick: (Int) -> Unit
) {
    val viewModel: CurrentChatViewModel = hiltViewModel()
    val chatRooms by viewModel.currentChatRooms.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("CrewChat", modifier = Modifier.weight(1f))
            Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(24.dp))
            Icon(Icons.Default.Addchart, contentDescription = null, modifier = Modifier.size(24.dp))
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(chatRooms, key = { it.roomNo }) { chatRoom ->
                CurrentChatRow(
                    modifier = Modifier.background(color = ColorWhite),
                    chatRoom = chatRoom,
                    onClick = {
                        onChatRoomClick(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun CurrentChatRow(
    chatRoom: CurrentChat,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = { onClick.invoke(chatRoom.roomNo) })
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(Color.White)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChatAvatar()

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = chatRoom.roomTitle.ifBlank { "No Title" },
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    if (!chatRoom.isOne && chatRoom.userNos.size > 1) {
                        Spacer(modifier = Modifier.width(6.dp))
                        UserCountBadge(count = chatRoom.userNos.size)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = chatRoom.lastedMsg.ifBlank { chatRoom.lastedMsgAttachName },
                    fontSize = 13.sp,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.width(86.dp)
            ) {
                Text(
                    text = chatRoom.displayLastMessageTime(),
                    fontSize = 11.sp,
                    color = TimeColorCurrentChat,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (chatRoom.unreadCount > 0) {
                    UnreadBadge(
                        count = chatRoom.unreadCount,
                        backgroundColor = chatRoom.unreadBadgeColor()
                    )
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(start = 56.dp),
            color = DivideColor.copy(alpha = 0.45f),
            thickness = 0.6.dp
        )
    }
}

@Composable
private fun ChatAvatar() {
    Box(modifier = Modifier.size(48.dp)) {
        DazoneImage(
            imageUrl = null,
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(8.dp))
                .align(Alignment.Center)
        )

        Box(
            modifier = Modifier
                .size(13.dp)
                .align(Alignment.BottomEnd)
                .clip(CircleShape)
                .background(Color.White)
                .border(1.3.dp, ColorPrimary, CircleShape)
        )
    }
}

@Composable
private fun UserCountBadge(count: Int) {
    Text(
        text = count.toString(),
        fontSize = 10.sp,
        color = SecondaryTextColor,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(TextViewTimeBg.copy(alpha = 0.55f))
            .padding(horizontal = 7.dp, vertical = 2.dp)
    )
}

@Composable
private fun UnreadBadge(
    count: Int,
    backgroundColor: Color
) {
    Box(
        modifier = Modifier
            .size(22.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (count > 99) "99+" else count.toString(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            maxLines = 1
        )
    }
}

private fun CurrentChat.displayLastMessageTime(): String {
    if (strLastedMsgDate.isNotBlank()) return strLastedMsgDate

    val millis = lastedMsgDate
        .substringAfter("Date(", "")
        .takeWhile { it.isDigit() }
        .toLongOrNull() ?: return ""

    val messageCalendar = Calendar.getInstance().apply { timeInMillis = millis }
    val currentCalendar = Calendar.getInstance()
    val pattern = if (messageCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR)) {
        "MM-dd hh:mm a"
    } else {
        "yyyy-MM-dd hh:mm a"
    }

    return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(millis))
}

private fun CurrentChat.unreadBadgeColor(): Color {
    val colors = listOf(
        BadgeBgColor,
        Color(0xFFC05BC8),
        Color(0xFF4BD6B1),
        Color(0xFF69D84E),
        Color(0xFFFF6F61)
    )
    return colors[kotlin.math.abs(roomNo) % colors.size]
}
