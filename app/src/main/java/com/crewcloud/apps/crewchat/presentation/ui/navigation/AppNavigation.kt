package com.crewcloud.apps.crewchat.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.crewcloud.apps.crewchat.presentation.ui.home.HomeScreen
import com.crewcloud.apps.crewchat.presentation.ui.login.LoginScreen
import com.crewcloud.apps.crewchat.presentation.ui.splash.SplashScreen

/**
 * Created by BM Anderson on 2/7/26.
 */
object CrewChatRoute {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val HOME = "home"
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
                    navController.navigate(CrewChatRoute.HOME) {
                        popUpTo(CrewChatRoute.SPLASH) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(CrewChatRoute.LOGIN) {
            LoginScreen {
                navController.navigate(CrewChatRoute.HOME) {
                    popUpTo(CrewChatRoute.SPLASH) {
                        inclusive = true
                    }
                }
            }
        }

        composable(CrewChatRoute.HOME) {
            HomeScreen()
        }
    }
}