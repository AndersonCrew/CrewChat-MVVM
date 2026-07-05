package com.crewcloud.apps.crewchat.domain.repository

import com.crewcloud.apps.crewchat.domain.model.AppPermission
import com.crewcloud.apps.crewchat.domain.model.PermissionResult

/**
 * Created by BM Anderson on 5/7/26.
 */
interface PermissionTracker {
    suspend fun checkPermissions(permissions: List<AppPermission>): Map<AppPermission, Boolean>
    suspend fun requestPermissions(permissions: List<AppPermission>): PermissionResult
}