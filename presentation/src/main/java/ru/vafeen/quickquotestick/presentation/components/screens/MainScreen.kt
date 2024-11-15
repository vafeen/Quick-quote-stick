package ru.vafeen.quickquotestick.presentation.components.screens

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import ru.vafeen.quickquotestick.presentation.MainActivity
import ru.vafeen.quickquotestick.presentation.components.screens.base.ComposableScreen
import ru.vafeen.quickquotestick.presentation.utils.CreateImageFromComposable

internal class MainScreen() : ComposableScreen {

    @Composable
    override fun Content() {

        val cor = rememberCoroutineScope()

        // Обработка кнопки или действия для сохранения изображения
        Column(modifier = Modifier.fillMaxSize()) {
            // Пример сохранения изображения
            CreateImageFromComposable()
        }
    }
}
