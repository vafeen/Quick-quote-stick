package ru.vafeen.quickquotestick.presentation.components.ui_utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.vafeen.quickquotestick.presentation.ui.theme.messageBackgroundColor
import ru.vafeen.quickquotestick.presentation.utils.shapeByIndex
import java.time.LocalDateTime
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import ru.vafeen.quickquotestick.domain.MessageController
import ru.vafeen.quickquotestick.presentation.ui.theme.Theme
import ru.vafeen.quickquotestick.resources.R as Res

@Composable
private fun RightAngledTriangleCanvas(
    size: Dp,
    color: Color
) {
    Canvas(modifier = Modifier.size(size)) {
        val width = this.size.width
        val height = this.size.height

        val path = Path().apply {
            moveTo(width, height)
            lineTo(width, 0f) // Верхний левый угол
            lineTo(0f, height) // Левый нижний угол
            close()
        }
        drawPath(path = path, color = color)
    }
}


@Composable
internal fun TGMessage(
    controller: MessageController,
    title: String? = null, text: String, index: Int, size: Int
) {
    val cornerBottomSize = 10.dp
    var messageMenu by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .padding(4.dp)
    ) {
        if (index == size - 1) {
            RightAngledTriangleCanvas(color = messageBackgroundColor, size = cornerBottomSize)
        }
        if (messageMenu) {
            DropdownMenu(modifier = Modifier.background(Theme.colors.buttonColor),
                expanded = messageMenu, onDismissRequest = { messageMenu = false }) {
                DropdownMenuItem(
                    text = { TextForThisTheme(text = stringResource(id = Res.string.delete)) },
                    onClick = {
                        controller.deleteByIndex(index)
                    })
            }
        }
        Card(
            modifier = Modifier.let {
                if (index != size - 1) it.padding(start = cornerBottomSize) else it
            },
            colors = CardDefaults.cardColors(containerColor = messageBackgroundColor),
            shape = shapeByIndex(index = index, size = size)
        ) {
            Column(modifier = Modifier
                .clickable {
                    messageMenu = true
                }
                .padding(7.dp)) {
                title?.let {
                    MessageTitle(title = title)
                }
                Spacer(modifier = Modifier.height(2.dp))
                MessageText(text = text)
            }
        }
    }
}