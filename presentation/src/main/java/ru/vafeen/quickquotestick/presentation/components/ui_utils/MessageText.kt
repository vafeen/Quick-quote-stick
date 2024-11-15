package ru.vafeen.quickquotestick.presentation.components.ui_utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import ru.vafeen.quickquotestick.presentation.ui.theme.FontSize
import ru.vafeen.quickquotestick.presentation.ui.theme.nameColor

@Composable
private fun StandardText(text: String, color: Color, fontSize: TextUnit) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize,
        style = MaterialTheme.typography.bodySmall.copy(
            fontWeight = FontWeight.Light
        )
    )
}
@Composable
internal fun MessageTitle(title: String) {
    StandardText(text = title, color = nameColor, fontSize = FontSize.normal)
}
@Composable
internal fun MessageText(text: String) {
    StandardText(text = text, color = Color.White, fontSize = FontSize.normal)
}