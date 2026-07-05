package com.crewcloud.apps.crewchat.domain.model

/**
 * Created by BM Anderson on 5/7/26.
 */
sealed class PermissionResult {
    object AllGranted : PermissionResult()

    data class Mixed(
        val statusMap: Map<AppPermission, Boolean>,
        val permanentlyDeniedExist: Boolean = false
    ) : PermissionResult()
}