package com.example.camerax_compose.ui.screens.screenshot_screen

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.camerax_compose.ui.screens.ui_components.BackButton

@Composable
fun ScreenShotScreen(
    screenShot: Bitmap,
    mainNav:NavController,
    onClick:()->Unit
) {
    val configuration = LocalConfiguration.current

    val width = configuration.screenWidthDp
    val height = configuration.screenHeightDp

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)){
        BackButton(
            modifier = Modifier
                .padding(30.dp)
                .align(Alignment.TopStart),
            nav = mainNav,
        ){
            onClick()
        }
        Image(
            bitmap = screenShot.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .height((height * 0.7).dp)
                .align(Alignment.Center)
        )
    }
}