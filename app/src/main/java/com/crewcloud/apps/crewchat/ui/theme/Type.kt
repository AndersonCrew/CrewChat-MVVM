package com.crewcloud.apps.crewchat.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.crewcloud.apps.crewchat.R

// Set of Material typography styles to start with

val CrewFontFamily = FontFamily(
    Font(R.font.gmarketsans_bold, FontWeight.Bold),
    Font(R.font.gmarketsans_medium, FontWeight.Medium),
    Font(R.font.gmarketsans_light, FontWeight.Light),
)

val AppTypography = Typography(
    bodyLarge = Typography().bodyLarge.copy(
        fontFamily = CrewFontFamily
    ),
    bodyMedium = Typography().bodyMedium.copy(
        fontFamily = CrewFontFamily
    ),

    titleMedium = Typography().titleMedium.copy(
        fontFamily = CrewFontFamily,
        fontWeight = FontWeight.SemiBold
    ),
)