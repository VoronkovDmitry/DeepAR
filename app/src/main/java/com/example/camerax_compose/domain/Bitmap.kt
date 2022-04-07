package com.example.camerax_compose.domain

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream

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