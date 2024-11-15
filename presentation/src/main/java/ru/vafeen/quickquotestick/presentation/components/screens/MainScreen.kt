package ru.vafeen.quickquotestick.presentation.components.screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import ru.vafeen.quickquotestick.presentation.MainActivity
import ru.vafeen.quickquotestick.presentation.components.screens.base.ComposableScreen
import ru.vafeen.quickquotestick.presentation.components.ui_utils.TGMessage
import ru.vafeen.quickquotestick.presentation.ui.theme.messageBackgroundColor
import ru.vafeen.quickquotestick.presentation.utils.CreateImageFromComposable

internal class MainScreen() : ComposableScreen {
    private val messages =
        listOf(
            "Тест",
            "Тест",
            "Тест",
        )

    @Composable
    override fun Content() {

        val cor = rememberCoroutineScope()

        // Обработка кнопки или действия для сохранения изображения
        Column(modifier = Modifier.fillMaxSize()) {
            // Пример сохранения изображения
//            CreateImageFromComposable()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Row(modifier = Modifier) {
//                    ResourceImage(
//                        modifier = Modifier.align(Alignment.Bottom),
//                        fileName = "niapoll.jpg"
//                    )
                    Column(modifier = Modifier) {
                        messages.forEachIndexed { index, it ->
                            TGMessage(
                                title = if (index == 0) "Покатаев Н.В." else null,
                                text = it,
                                index = index,
                                size = messages.size
                            )
                        }
                    }
                }
            }
        }
    }
}
