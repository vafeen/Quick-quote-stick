package ru.vafeen.quickquotestick.presentation.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.ColorUtils

internal fun Color.suitableColor(): Color =
    if (this.isDark()) Color.White else Color.Black

internal fun Color.isDark(): Boolean = ColorUtils.calculateLuminance(this.toArgb()) < 0.4
