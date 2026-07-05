package com.crewcloud.apps.crewchat.data.repository

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.crewcloud.apps.crewchat.domain.model.AppPermission
import com.crewcloud.apps.crewchat.domain.model.PermissionResult
import com.crewcloud.apps.crewchat.domain.repository.PermissionTracker
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject

/**
 * Created by BM Anderson on 5/7/26.
 */
class AndroidPermissionTracker @Inject constructor(
    private val activity: ComponentActivity
): PermissionTracker {
    private var callback: ((Map<String, Boolean>) -> Unit)? = null
    private val launcher = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        callback?.invoke(results)
    }
    override suspend fun checkPermissions(permissions: List<AppPermission>): Map<AppPermission, Boolean> {
        return permissions.associateWith { perm ->
            ContextCompat.checkSelfPermission(
                activity,
                mapToManifest(perm)
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    override suspend fun requestPermissions(permissions: List<AppPermission>): PermissionResult {
        val manifestStrings = permissions.map { mapToManifest(it) }.toTypedArray()

        val rawResults = suspendCancellableCoroutine { continuation ->
            callback = { results ->
                continuation.resume(results, onCancellation = null)
            }
            launcher.launch(manifestStrings)
        }

        callback = null

        val domainResults = permissions.associateWith { perm ->
            rawResults[mapToManifest(perm)] == true
        }

        val allGranted = domainResults.values.all { it }
        return if (allGranted) {
            PermissionResult.AllGranted
        } else {
            val hasPermanentlyDenied = permissions.any { perm ->
                val manifestPerm = mapToManifest(perm)
                !activity.shouldShowRequestPermissionRationale(manifestPerm) && rawResults[manifestPerm] == false
            }

            PermissionResult.Mixed(domainResults, hasPermanentlyDenied)
        }
    }

    @SuppressLint("InlinedApi")
    private fun mapToManifest(permission: AppPermission): String {
        return when (permission) {
            AppPermission.CAMERA -> Manifest.permission.CAMERA
            AppPermission.NOTIFICATION -> Manifest.permission.POST_NOTIFICATIONS
        }
    }
}