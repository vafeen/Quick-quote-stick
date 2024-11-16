package ru.vafeen.quickquotestick.presentation.utils

import androidx.compose.ui.graphics.Color

internal fun Color.suitableColor(): Color =
    if (this.isDark()) Color.White else Color.Black

internal fun Color.isDark(): Boolean =
    0.299 * red + 0.587 * green + 0.114 * blue <= 0.5