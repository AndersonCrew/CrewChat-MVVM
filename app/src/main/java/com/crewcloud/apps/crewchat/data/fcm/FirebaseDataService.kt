package com.crewcloud.apps.crewchat.data.fcm

import android.util.Log
import com.crewcloud.apps.crewchat.domain.usecase.FcmDataUseCase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by BM Anderson on 5/7/26.
 */
@AndroidEntryPoint
class FirebaseDataService @Inject constructor(): FirebaseMessagingService() {

    @Inject lateinit var fcmDataUseCase: FcmDataUseCase

    @Inject lateinit var notificationHelper: NotificationHelper
    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        serviceScope.launch {
            fcmDataUseCase.invoke(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.d("FirebaseDataService", "onMessageReceived - RemoteMessage = $message")
        val data = message.data
        if (data.isNotEmpty()) {
            val title = data["title"] ?: "Tin nhắn mới"
            val body = data["body"] ?: ""
            val screenToOpen = data["click_action"] ?: "CHAT_ROOM"
            val chatRoomId = data["chat_room_id"] ?: ""

            notificationHelper.showNotification(title, body, screenToOpen, chatRoomId)
        }
    }
}