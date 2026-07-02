package com.crewcloud.apps.crewchat.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.crewcloud.apps.crewchat.ui.login.LoginScreen
import com.crewcloud.apps.crewchat.ui.splash.SplashScreen

/**
 * Created by BM Anderson on 2/7/26.
 */
object CrewChatRoute {
    const val SPLASH = "splash"
    const val LOGIN = "login"
}

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = CrewChatRoute.SPLASH,
    ) {
        composable(CrewChatRoute.SPLASH) {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(CrewChatRoute.LOGIN) {
                        popUpTo(CrewChatRoute.SPLASH) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToHome = {

                }
            )
        }

        composable(CrewChatRoute.LOGIN) {
            LoginScreen()
        }
    }
}