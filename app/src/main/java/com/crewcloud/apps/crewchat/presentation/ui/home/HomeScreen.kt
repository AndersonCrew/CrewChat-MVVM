package com.crewcloud.apps.crewchat.presentation.ui.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.crewcloud.apps.crewchat.presentation.core.navigation.HomeTab
import com.crewcloud.apps.crewchat.presentation.core.navigation.HomeTab.Companion.getIconForTab
import com.crewcloud.apps.crewchat.presentation.ui.home.current_chat.CurrentChatScreen
import com.crewcloud.apps.crewchat.presentation.ui.home.company.CompanyScreen

/**
 * Created by BM Anderson on 3/7/26.
 */
@Composable
fun HomeScreen(
    onChatRoomClick: (Int) -> Unit
) {
    val childNavController = rememberNavController()
    val navBackStackEntry by childNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(modifier = Modifier.fillMaxWidth()) {
                HomeTab.getTabs().forEach { tab ->
                    val isSelected = currentDestination?.hierarchy?.any {
                        it.route?.contains(tab::class.simpleName ?: "") == true
                    } == true

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            childNavController.navigate(tab) {
                                popUpTo(childNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(imageVector = getIconForTab(tab), contentDescription = null) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = childNavController,
            startDestination = HomeTab.CompanyTab,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            composable<HomeTab.CompanyTab> { CompanyScreen() }
            composable<HomeTab.ChatTab> {
                CurrentChatScreen { roomNo ->
                    onChatRoomClick.invoke(roomNo)
                }
            }
            composable<HomeTab.FavoriteTab> { Text("FavoriteTab") }
            composable<HomeTab.SettingTab> { Text("SettingTab") }
        }
    }
}