package ru.vafeen.quickquotestick.presentation.utils

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch
import ru.vafeen.quickquotestick.presentation.components.ui_utils.TextForThisTheme

@Composable
fun CreateImageFromComposable() {
    val coroutineScope = rememberCoroutineScope()
    val graphicsLayer = rememberGraphicsLayer()
    var imageBitmap: ImageBitmap? by remember { mutableStateOf(null) }
    val context = LocalContext.current

    // Запрашиваем разрешение на доступ к внутренней памяти при запуске
    LaunchedEffect(null) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                (context as Activity),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }
    }

    Column {
        if (imageBitmap != null) {
            TextForThisTheme("тут должна быть картинка")
            Image(
                modifier = Modifier
                    .background(Color.White)
                    .padding(1.dp),
                bitmap = imageBitmap!!, contentDescription = null
            )
        }
        Box(
            modifier = Modifier
                .drawWithContent {
                    // call record to capture the content in the graphics layer
                    graphicsLayer.record {
                        // draw the contents of the composable into the graphics layer
                        this@drawWithContent.drawContent()
                    }
                    // draw the graphics layer on the visible canvas
                    drawLayer(graphicsLayer)
                }
                .clickable {
                    coroutineScope.launch {
                        val bitmap = graphicsLayer.toImageBitmap()
                        imageBitmap = bitmap
                        saveImageToGallery(context, bitmap)
                    }
                }
                .background(Color.Red)
        ) {
            TextForThisTheme("Hello Android", fontSize = 20.sp)
        }
    }
}



// Функция для сохранения ImageBitmap в галерею
private fun saveImageToGallery(context: Context, bitmap: ImageBitmap) {
    val imageFileName = "saved_image_${System.currentTimeMillis()}.png"

    // Создаем ContentValues для нового изображения
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, imageFileName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/QuickQuotesStick") // Сохраняем в папке "QuickQuotesStick"
    }

    // Получаем URI для сохранения в папку Pictures
    val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    if (uri != null) {
        try {
            // Открываем поток вывода в файл
            context.contentResolver.openOutputStream(uri)?.use { outStream ->
                val bitmapAndroid = bitmap.asAndroidBitmap() // Преобразуем ImageBitmap в Bitmap
                bitmapAndroid.compress(Bitmap.CompressFormat.PNG, 100, outStream)
                outStream.flush()
            }
            // Показываем уведомление о сохранении
            showSaveNotification(context, imageFileName)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Ошибка при сохранении изображения", Toast.LENGTH_SHORT).show()
        }
    } else {
        Toast.makeText(context, "Не удалось сохранить изображение", Toast.LENGTH_SHORT).show()
    }
}

private fun showSaveNotification(context: Context, imageFileName: String) {
    Toast.makeText(context, "Изображение '$imageFileName' успешно сохранено в галерее!", Toast.LENGTH_SHORT).show()
}

