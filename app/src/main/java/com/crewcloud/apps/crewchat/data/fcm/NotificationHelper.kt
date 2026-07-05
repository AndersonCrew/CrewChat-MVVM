package com.crewcloud.apps.crewchat.data.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.crewcloud.apps.crewchat.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by BM Anderson on 5/7/26.
 */
class NotificationHelper @Inject constructor(
    @param:ApplicationContext private val context: Context
) {
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val CHANNEL_CHAT_ID = "channel_crew_chat"
        const val CHANNEL_CHAT_NAME = "CrewChat Messages"
    }

    fun showNotification(title: String, body: String, screen: String, targetId: String) {
        createNotificationChannel()

        // 1. Tạo Intent trỏ về MainActivity duy nhất của App
        val intent = Intent(context, MainActivity::class.java).apply {
            // Cờ này giúp nếu App đang mở thì không khởi động lại MainActivity mà chỉ gọi vào hàm onNewIntent
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            // Đóng gói data từ FCM vào Intent
            putExtra("SCREEN_TO_OPEN", screen)
            putExtra("TARGET_ID", targetId)
        }

        // 2. Bọc Intent bằng PendingIntent (Bắt buộc dùng FLAG_IMMUTABLE từ Android 12 trở lên vì lý do bảo mật)
        val pendingIntent = PendingIntent.getActivity(
            context,
            targetId.hashCode(), // Định danh để các thông báo phòng chat khác nhau không đè lên nhau
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // 3. Build và hiển thị thông báo lên máy
        val builder = NotificationCompat.Builder(context, CHANNEL_CHAT_ID)
            .setSmallIcon(android.R.drawable.stat_notify_chat) // Icon nhỏ của bạn
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true) // Click xong tự biến mất
            .setContentIntent(pendingIntent) // Gắn cú click vào đây

        notificationManager.notify(targetId.hashCode(), builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager.getNotificationChannel(CHANNEL_CHAT_ID) == null) {
                val channel = NotificationChannel(
                    CHANNEL_CHAT_ID,
                    CHANNEL_CHAT_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Kênh nhận thông báo tin nhắn nội bộ"
                }
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}