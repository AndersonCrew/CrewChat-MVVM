package com.crewcloud.apps.crewchat.presentation.core.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crewcloud.apps.crewchat.domain.model.AppPermission
import com.crewcloud.apps.crewchat.domain.repository.PermissionTracker
import com.crewcloud.apps.crewchat.presentation.core.navigation.GlobalEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by BM Anderson on 5/7/26.
 */
@HiltViewModel
class GlobalNotificationViewModel @Inject constructor(): ViewModel() {
    private val _events = MutableStateFlow<GlobalEvent?>(null)
    val events = _events.asStateFlow()
    val appPermissions = listOf(
        AppPermission.NOTIFICATION
    )

    fun navigateTo(screen: String, targetId: String) {
       // _navigationEvent.value = NavigationEvent(screen, targetId)
    }

    fun clearNavigationEvent() {

    }

    fun checkAppPermissions(permissionTracker: PermissionTracker) = viewModelScope.launch {
        val checkResult = permissionTracker.checkPermissions(appPermissions)

        if (checkResult[AppPermission.NOTIFICATION] == false) {
            _events.emit(GlobalEvent.RequestPermission(appPermissions))
        }
    }
}