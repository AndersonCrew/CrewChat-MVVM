package com.crewcloud.apps.crewchat.presentation.core.navigation

import androidx.activity.ComponentActivity
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.crewcloud.apps.crewchat.presentation.core.model.GlobalNotificationViewModel
import com.crewcloud.apps.crewchat.presentation.ui.home.HomeScreen
import com.crewcloud.apps.crewchat.presentation.ui.login.LoginScreen
import com.crewcloud.apps.crewchat.presentation.ui.splash.SplashScreen
import kotlinx.serialization.Serializable
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.crewcloud.apps.crewchat.presentation.ui.home.chatting.ChattingScreen

/**
 * Created by BM Anderson on 2/7/26.
 */

@Serializable
data class ChattingScreen(val roomNo: Int)

@Serializable
object Splash
@Serializable
object Login
@Serializable
object Home

//Tab for HomeScreen
sealed interface HomeTab {

    @Serializable
    object CompanyTab : HomeTab
    @Serializable
    object ChatTab : HomeTab
    @Serializable
    object FavoriteTab : HomeTab
    @Serializable
    object SettingTab : HomeTab

    companion object {
        fun getTabs(): List<HomeTab> = listOf(CompanyTab, ChatTab, FavoriteTab, SettingTab)
        fun getIconForTab(tab: HomeTab): ImageVector {
            return when (tab) {
                CompanyTab -> Icons.Default.Home
                ChatTab -> Icons.Default.Chat
                FavoriteTab -> Icons.Default.Favorite
                SettingTab -> Icons.Default.Settings
            }
        }

        fun getStringTab(tab: HomeTab): String {
            return when (tab) {
                CompanyTab -> "Company"
                ChatTab -> "Chat"
                FavoriteTab -> "Favorite"
                SettingTab -> "Settings"
            }
        }
    }
}

@Composable
fun AppNavigation(
    activity: ComponentActivity,
    navController: NavHostController = rememberNavController(),
) {
    val viewModel: GlobalNotificationViewModel = hiltViewModel(
        viewModelStoreOwner = activity
    )
    val navigationEvent by viewModel.events.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        navigationEvent?.let { event ->
            when (event) {
                is GlobalEvent.NavigateToChatDetail -> {}
                else -> {}
            }
        }
    }
    NavHost(
        navController = navController,
        startDestination = Splash,
    ) {
        composable<Splash> {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Login) {
                        popUpTo(Splash) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Home) {
                        popUpTo(Splash) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Login> {
            LoginScreen {
                navController.navigate(Home) {
                    popUpTo(Splash) {
                        inclusive = true
                    }
                }
            }
        }

        composable<ChattingScreen> {
            ChattingScreen()
        }

        composable<Home> {
            HomeScreen {
                navController.navigate(ChattingScreen(it))
            }
        }
    }
}