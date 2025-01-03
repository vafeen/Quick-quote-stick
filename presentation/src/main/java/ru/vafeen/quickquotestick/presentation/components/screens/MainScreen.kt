package ru.vafeen.quickquotestick.presentation.components.screens

import android.R.attr.onClick
import android.R.id.message
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
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
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.launch
import ru.vafeen.quickquotestick.domain.MessageController
import ru.vafeen.quickquotestick.presentation.components.screens.base.ComposableScreen
import ru.vafeen.quickquotestick.presentation.components.ui_utils.TGMessage
import ru.vafeen.quickquotestick.presentation.components.ui_utils.TextForThisTheme
import ru.vafeen.quickquotestick.presentation.ui.theme.Theme
import ru.vafeen.quickquotestick.presentation.utils.saveImageToGallery
import ru.vafeen.quickquotestick.presentation.utils.suitableColor
import ru.vafeen.quickquotestick.resources.R as Resources

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
        val tfColors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedTextColor = Theme.colors.oppositeTheme,
            unfocusedTextColor = Theme.colors.oppositeTheme,
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color.Gray
        )
        var name by remember { mutableStateOf("Click to change name") }
        var isNameInChanging by remember { mutableStateOf(false) }
        val graphicsLayer = rememberGraphicsLayer()
        var imageBitmap: ImageBitmap? by remember { mutableStateOf(null) }
        val context = LocalContext.current
        val kc by keyboardAsState()
        var message by remember { mutableStateOf("") }
        var messages = remember {
            mutableStateListOf(
                "Click to delete",
            )
        }

        val messageController = object : MessageController {
            override fun deleteByIndex(index: Int) {
                try {
                    messages.remove(messages[index])
                } catch (_: Exception) {
                }
            }
        }
        val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = messages.lastIndex)
        val cor = rememberCoroutineScope()
        LaunchedEffect(kc) {
            if (kc && messages.isNotEmpty()) cor.launch {
                lazyListState.scrollToItem(messages.lastIndex)
            }
        }
        var expanded by remember { mutableStateOf(false) }
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Theme.colors.mainColor),
                    title = {
                        if (isNameInChanging) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                OutlinedTextField(
                                    colors = tfColors,
                                    value = name,
                                    onValueChange = { name = it },
                                    modifier = Modifier
                                        .weight(1f),
                                )
                                IconButton(onClick = { isNameInChanging = false }) {
                                    Icon(
                                        imageVector = Icons.Default.Done,
                                        contentDescription = null,
                                        tint = Theme.colors.oppositeTheme
                                    )
                                }
                            }
                        } else {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = name,
                                    color = Theme.colors.mainColor.suitableColor(),
                                    modifier = Modifier.clickable {
                                        isNameInChanging = true
                                    }
                                )
                            }
                        }
                    },
                    actions = {
                        // Три точки (иконка меню)
                        if (messages.isNotEmpty())
                            IconButton(onClick = { expanded = true }) {
                                Icon(
                                    Icons.Default.MoreVert,
                                    contentDescription = "Меню",
                                    tint = Theme.colors.mainColor.suitableColor()
                                )
                            }

                        // Выпадающее меню
                        DropdownMenu(modifier = Modifier.background(Theme.colors.singleTheme),
                            expanded = expanded, onDismissRequest = { expanded = false }) {
                            DropdownMenuItem(text = {
                                TextForThisTheme(text = stringResource(id = Resources.string.save_dialog_as_image))
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
                BottomAppBar(containerColor = Theme.colors.mainColor) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            colors = tfColors,
                            label = { Text(text = stringResource(id = Resources.string.enter_message)) },
                            value = message,
                            onValueChange = { message = it },
                            modifier = Modifier
                                .weight(1f),
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
                            Icon(
                                imageVector = Icons.Default.Send, contentDescription = "Send",
                                tint = Theme.colors.oppositeTheme
                            )
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
                ) {
                    LazyColumn(
                        state = lazyListState,
                    ) {
                        itemsIndexed(messages) { index, it ->
                            TGMessage(
                                controller = messageController,
                                title = if (index == 0) name else null,
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
