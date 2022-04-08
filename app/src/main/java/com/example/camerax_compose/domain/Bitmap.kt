package com.example.camerax_compose.domain

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import com.example.camerax_compose.BuildConfig
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

fun String.toBitmap():Bitmap{
    val photo = Base64.decode(this, Base64.NO_WRAP)
    return BitmapFactory.decodeByteArray(photo, 0,photo.size)
}

fun Bitmap.encodeToString():String{
    val outputStream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray,Base64.NO_WRAP)
}

fun saveBitmapInStorage(bitmap: Bitmap, context: Context) {
    val filename = "QR_"+"${System.currentTimeMillis()}.jpg"
    var fos: OutputStream? = null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.contentResolver?.also { resolver ->
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            val imageUri: Uri? =
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }
    } else {
        val imagesDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, filename)
        fos = FileOutputStream(image)
    }
    fos?.use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
    }
}

fun shareBitmap(context: Context):Intent?{
    try {
        val file = File("")
        if(file.exists()) {
            val uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file)
            val intent = Intent(Intent.ACTION_SEND)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setType("image/*")
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return intent
        }
        else
            return null
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        return null
    }
}
