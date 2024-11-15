package ru.vafeen.quickquotestick.presentation.components.ui_utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.vafeen.quickquotestick.presentation.ui.theme.Theme
import ru.vafeen.quickquotestick.presentation.ui.theme.messageBackgroundColor
import ru.vafeen.quickquotestick.presentation.utils.shapeByIndex

@Composable
internal fun TGMessage(title: String? = null, text: String, index: Int, size: Int) {
    val cornerBottomSize = 10.dp
    Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.padding(4.dp)) {
        if (index == size - 1) {
            Card(
                modifier = Modifier.size(cornerBottomSize).background(messageBackgroundColor),
                shape = RoundedCornerShape(bottomEnd = cornerBottomSize),
                colors = CardDefaults.cardColors(containerColor = Theme.colors.singleTheme)
            ) {}
        }
        Card(
            modifier = Modifier.let {
                if (index != size - 1) it.padding(start = cornerBottomSize) else it
            },
            colors = CardDefaults.cardColors(containerColor = messageBackgroundColor),
            shape = shapeByIndex(index = index, size = size)
        ) {
            Column(modifier = Modifier.padding(7.dp)) {
                title?.let {
                        MessageTitle(title = title)
                }
                Spacer(modifier = Modifier.height(2.dp))
                MessageText(text = text)
            }
        }
    }
}
