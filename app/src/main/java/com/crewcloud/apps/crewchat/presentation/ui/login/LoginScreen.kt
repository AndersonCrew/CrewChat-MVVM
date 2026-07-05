package com.crewcloud.apps.crewchat.presentation.ui.login

import android.annotation.SuppressLint
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.crewcloud.apps.crewchat.R
import com.crewcloud.apps.crewchat.presentation.core.component.DazoneDialog
import com.crewcloud.apps.crewchat.presentation.core.component.LoadingDialog
import com.crewcloud.apps.crewchat.presentation.core.theme.ColorHint
import com.crewcloud.apps.crewchat.presentation.core.theme.ColorWhite
import com.crewcloud.apps.crewchat.presentation.core.theme.CrewChatMVVMTheme
import com.crewcloud.apps.crewchat.presentation.core.theme.CrewFontFamily
import com.crewcloud.apps.crewchat.presentation.core.theme.Dimen.ButtonHeight

/**
 * Created by BM Anderson on 2/7/26.
 */
@SuppressLint("HardwareIds")
@Composable
fun LoginScreen(
    onNavigateToHome: () -> Unit
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val focusManager = LocalFocusManager.current
    var dialogMessage by remember { mutableStateOf<String?>(null) }
    val TAG = "LoginScreen"
    val androidId =
        Settings.Secure.getString(LocalContext.current.contentResolver, Settings.Secure.ANDROID_ID)

    LaunchedEffect(Unit) {
        viewModel.init()
    }

    LaunchedEffect(viewModel.uiEvent) {
        viewModel.uiEvent.collect {
            when (it) {
                is LoginEvent.LoginError -> dialogMessage = it.message
                is LoginEvent.LoginSuccess -> onNavigateToHome.invoke()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorWhite)
            .pointerInput(Unit) {
                detectTapGestures { _ ->
                    focusManager.clearFocus()
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        Log.d(TAG, "Recomposition Parent")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(
                22.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.img_logo_login),
                modifier = Modifier.height(120.dp),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(8.dp))
                    .height(52.dp)
                    .fillMaxWidth()
                    .background(color = ColorWhite, shape = RoundedCornerShape(8.dp))
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    value = uiState.domain,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = viewModel::onDomainChanged,
                    placeholder = {
                        Text(
                            stringResource(R.string.string_server_site),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = CrewFontFamily,
                                color = ColorHint,
                                fontWeight = FontWeight.Light,
                            )
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = ColorHint,
                    )
                )
            }

            Box(
                modifier = Modifier
                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(8.dp))
                    .height(52.dp)
                    .fillMaxWidth()
                    .background(color = ColorWhite, shape = RoundedCornerShape(8.dp))
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    value = uiState.userName,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = viewModel::onUserNameChanged,
                    placeholder = {
                        Text(
                            stringResource(R.string.login_username),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = CrewFontFamily,
                                color = ColorHint,
                                fontWeight = FontWeight.Light,
                            )
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = ColorHint,
                    )
                )
            }

            Box(
                modifier = Modifier
                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(8.dp))
                    .height(52.dp)
                    .fillMaxWidth()
                    .background(color = ColorWhite, shape = RoundedCornerShape(8.dp))
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    value = uiState.password,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = viewModel::onPasswordChanged,
                    placeholder = {
                        Text(
                            stringResource(R.string.login_password),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = CrewFontFamily,
                                color = ColorHint,
                                fontWeight = FontWeight.Light,
                            )
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = ColorHint,
                    )
                )
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ButtonHeight), onClick = {
                    viewModel.onLogin(androidId)
                }
            ) {
                Text("Login")
            }
        }

        dialogMessage?.let {
            DazoneDialog(
                content = it,
                positiveStr = stringResource(R.string.string_ok),
                onPositiveClick = {
                    dialogMessage = null
                },
                onNegativeClick = {},
            )
        }

        if (uiState.isLoading) {
            Log.d(TAG, "LoadingDialog")
            LoadingDialog()
        }
    }

}
