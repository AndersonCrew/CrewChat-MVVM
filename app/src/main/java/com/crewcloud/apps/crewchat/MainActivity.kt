package com.crewcloud.apps.crewchat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.crewcloud.apps.crewchat.ui.navigation.AppNavigation
import com.crewcloud.apps.crewchat.ui.theme.CrewChatMVVMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CrewChatMVVMTheme {
                AppNavigation()
            }
        }
    }
}
