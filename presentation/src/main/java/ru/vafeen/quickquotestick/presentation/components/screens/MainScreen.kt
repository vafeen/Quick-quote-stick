package ru.vafeen.quickquotestick.presentation.components.screens

import android.R.attr.onClick
import android.R.id.message
import android.graphics.BitmapFactory
import android.view.ViewTreeObserver
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Recomposer
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.launch
import ru.vafeen.quickquotestick.presentation.components.screens.base.ComposableScreen
import ru.vafeen.quickquotestick.presentation.components.ui_utils.TGMessage
import ru.vafeen.quickquotestick.presentation.components.ui_utils.TextForThisTheme
import ru.vafeen.quickquotestick.presentation.ui.theme.Theme
import ru.vafeen.quickquotestick.presentation.utils.saveImageToGallery

internal class MainScreen() : ComposableScreen {
    @Composable
    fun keyboardAsState(): State<Boolean> {
        val view = LocalView.current
        var isImeVisible by remember { mutableStateOf(false) }

        DisposableEffect(LocalWindowInfo.current) {
            val listener = ViewTreeObserver.OnPreDrawListener {
                isImeVisible = ViewCompat.getRootWindowInsets(view)
                    ?.isVisible(WindowInsetsCompat.Type.ime()) == true
                true
            }
            view.viewTreeObserver.addOnPreDrawListener(listener)
            onDispose {
                view.viewTreeObserver.removeOnPreDrawListener(listener)
            }
        }
        return rememberUpdatedState(isImeVisible)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val coroutineScope = rememberCoroutineScope()
        val graphicsLayer = rememberGraphicsLayer()
        var imageBitmap: ImageBitmap? by remember { mutableStateOf(null) }
        val context = LocalContext.current
        val kc by keyboardAsState()
        var message by remember { mutableStateOf("") }
        var messages = remember {
            mutableStateListOf(
                "Тест",
            )
        }
        val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = messages.lastIndex)
        val cor = rememberCoroutineScope()
        LaunchedEffect(kc) {
            if (kc) cor.launch {
                lazyListState.scrollToItem(messages.lastIndex)
            }
        }
        var expanded by remember { mutableStateOf(false) }
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Theme.colors.mainColor),
                    title = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            TextForThisTheme(text = "Покатаев Н.В.")
                        }
                    },
                    actions = {
                        // Три точки (иконка меню)
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Меню")
                        }

                        // Выпадающее меню
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            DropdownMenuItem(text = {
                                Text("Сохранить диалог как файл")
                            }, onClick = {
                                expanded = false
                                cor.launch {
                                    val bitmap = graphicsLayer.toImageBitmap()
                                    imageBitmap = bitmap
                                    saveImageToGallery(context, bitmap)
                                }
                            })
                        }
                    },
                )
            }, bottomBar = {
                BottomAppBar {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = message,
                            onValueChange = { message = it },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                        )
                        IconButton(onClick = {
                            if (message.isNotEmpty()) {
                                messages.add(message)
                                message = ""
                                cor.launch {
                                    lazyListState.animateScrollToItem(messages.lastIndex)
                                }
                            }
                        }) {
                            Icon(Icons.Default.Send, contentDescription = "Send")
                        }
                    }
                }
            }) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(Theme.colors.singleTheme)
            ) {
                Box(modifier = Modifier
                    .drawWithContent {
                        // call record to capture the content in the graphics layer
                        graphicsLayer.record {
                            // draw the contents of the composable into the graphics layer
                            this@drawWithContent.drawContent()
                        }
                        // draw the graphics layer on the visible canvas
                        drawLayer(graphicsLayer)
                    }
//                .background(Color.Transparent)
                ) {
                    LazyColumn(state = lazyListState) {
                        itemsIndexed(messages) { index, it ->
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
