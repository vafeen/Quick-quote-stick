package ru.vafeen.quickquotestick.presentation.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

internal fun shapeByIndex(index: Int, size: Int): Shape {
    val no = 0
    val normal = 5
    val full = 15
    var topStart = 0.dp
    var topEnd = full.dp
    var bottomEnd = full.dp
    var bottomStart = 0.dp
    topStart = if (index == 0) full.dp else normal.dp
    bottomStart = if (index == size - 1) no.dp else normal.dp
    return RoundedCornerShape(
        topStart = topStart,
        topEnd = topEnd,
        bottomStart = bottomStart,
        bottomEnd = bottomEnd
    )
}