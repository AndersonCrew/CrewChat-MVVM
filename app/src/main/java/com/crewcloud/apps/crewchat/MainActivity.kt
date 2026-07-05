package com.crewcloud.apps.crewchat

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.crewcloud.apps.crewchat.domain.repository.PermissionTracker
import com.crewcloud.apps.crewchat.presentation.core.navigation.AppNavigation
import com.crewcloud.apps.crewchat.presentation.core.navigation.GlobalEvent
import com.crewcloud.apps.crewchat.presentation.core.theme.CrewChatMVVMTheme
import com.crewcloud.apps.crewchat.presentation.core.viewmodel.GlobalNotificationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var permissionTracker: PermissionTracker
    val viewModel: GlobalNotificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        handleNotificationIntent(intent)

        setContent {
            CrewChatMVVMTheme {
                AppNavigation(this)
            }
        }

        setupObservers()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.events.collect { event ->
                    if (event is GlobalEvent.RequestPermission) {
                        permissionTracker.requestPermissions(event.permissions)
                    }
                }
            }
        }

        viewModel.checkAppPermissions(permissionTracker)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleNotificationIntent(intent)
    }

    private fun handleNotificationIntent(intent: Intent?) {
        val screenToOpen = intent?.getStringExtra("SCREEN_TO_OPEN")
        val targetId = intent?.getStringExtra("TARGET_ID")


        if (!screenToOpen.isNullOrEmpty()) {
            //  viewModel.navigateTo(screenToOpen, targetId)
            intent.removeExtra("SCREEN_TO_OPEN")
            intent.removeExtra("TARGET_ID")
        }
    }

    companion object {
        const val TAG = "CrewChat MVVM"
    }
}
