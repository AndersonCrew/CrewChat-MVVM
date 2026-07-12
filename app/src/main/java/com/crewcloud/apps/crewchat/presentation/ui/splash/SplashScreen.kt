package com.crewcloud.apps.crewchat.presentation.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.crewcloud.apps.crewchat.presentation.core.component.LoadingDialog
import com.crewcloud.apps.crewchat.presentation.core.theme.ColorWhite

/**
 * Created by BM Anderson on 2/7/26.
 */

@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val viewModel: SplashViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect {
            when (it) {
                SplashEvent.NavigateToHome -> onNavigateToHome.invoke()
                SplashEvent.NavigateToLogin -> onNavigateToLogin.invoke()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.check()
    }

    Scaffold(modifier = Modifier.fillMaxSize().background(ColorWhite)) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LoadingDialog ()
        }
    }
}