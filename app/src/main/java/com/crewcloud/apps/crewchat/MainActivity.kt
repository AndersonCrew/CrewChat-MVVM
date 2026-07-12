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
import com.crewcloud.apps.crewchat.presentation.core.model.GlobalNotificationViewModel
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

/*
* 1. Tại vì khi lâý dư liệu từ Room, tao có thể update dư liệu đó từ nhiều nơi khác nhau , nếu return về 1 flow thi compose sẽ được update tự động mà ko cần gọi lại hay refresh data
* ko biết tại sao Room lại biết db có sự thay đổi
* 2. có thể gọi query 1000 lần vì flow là 1 cold stream nó chạy khi có collector va ko lưu dư liệu , vì thế ta thường dùng stateIn hoặc sharedIn để tạo 1 kết nối từ viewmodel tới flow băng 1 kêt nối , lúc này ơ viewmodel se biến flow thành 1 hot stream
* và collector tư compose sẽ collect hot stream ở VM thay vì collect trực tiếp tới flow ở repo
* 3.vì quá trình insert vào db có thể mất nhiều thơi gian, vì thê room sẽ tự chuyển thread sang IO thread và ta dung ham suspend func la để tạm dừng mà ko block thread , khi nao insert xong
* thì resume chạy tiếp mà ko ảnh hương tới thread
* 4.transaction dùng khi update 1 data trong db mà ko muốn bị mất dư liệu trong qua trình update nếu thất bại, thi nó sẽ convẻt lại data cũ
* 5. chính xác la nó sẽ emit 3 lần , nhưng ta có thể dùng operator như conflate để huỷ bỏ giá trị emit chỉ emit giá trị cuối cùng cho collecter*/
