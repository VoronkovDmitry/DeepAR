package com.example.camerax_compose.ui.screens.screenshot_screen

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.camerax_compose.domain.saveBitmapInStorage
import com.example.camerax_compose.ui.screens.ui_components.BackButton
import com.example.camerax_compose.R

@Composable
fun ScreenShotScreen(
    screenShot: Bitmap,
    onBackButtonClicked:()->Unit,
) {
    val configuration = LocalConfiguration.current

    val width = configuration.screenWidthDp
    val height = configuration.screenHeightDp

    val context = LocalContext.current

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)){
        BackButton(
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.TopStart),
            onClick = onBackButtonClicked
        )
        Image(
            bitmap = screenShot.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .height((height * 0.8).dp)
                .align(Alignment.Center)
        )

        IconButton(
            onClick = {
                saveBitmapInStorage(screenShot, context = context)
                Toast.makeText(context, "Picture Saved in Gallery", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.save),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}