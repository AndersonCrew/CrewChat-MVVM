package com.crewcloud.apps.crewchat.domain.usecase

import com.crewcloud.apps.crewchat.domain.repository.AuthRepository
import com.crewcloud.apps.crewchat.domain.repository.NotificationRepository
import javax.inject.Inject

/**
 * Created by BM Anderson on 5/7/26.
 */
class FcmDataUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository,
    private val authentication: AuthRepository
){
    suspend operator fun invoke(fcm: String) {
        val oldToken = notificationRepository.getFCMToken()
        if(oldToken == fcm) return

        notificationRepository.saveFcmToken(fcm)
        if(!authentication.getSessionId().isNullOrEmpty()) {
            notificationRepository.sendFCMToServer(fcm)
        }
    }
}