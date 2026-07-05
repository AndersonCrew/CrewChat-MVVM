package com.crewcloud.apps.crewchat.presentation.core.navigation

import com.crewcloud.apps.crewchat.domain.model.AppPermission

/**
 * Created by BM Anderson on 5/7/26.
 */
sealed class GlobalEvent {
    data class NavigateToChatDetail(
        val chatId: Long
    ) : GlobalEvent()

    data class RequestPermission(val permissions: List<AppPermission>) : GlobalEvent()
}