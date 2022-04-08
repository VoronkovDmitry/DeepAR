package com.example.camerax_compose.ui.screens.video_screen

import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import com.example.camerax_compose.ui.screens.camera_screen.CameraViewModel
import com.example.camerax_compose.ui.screens.ui_components.BackButton
import java.io.File

@Composable
fun VideoScreen(
    file: File,
    onClick:() -> Unit) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val configuration = LocalConfiguration.current

    val width = configuration.screenWidthDp
    val height = configuration.screenHeightDp


    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black)){
        BackButton(modifier = Modifier
            .padding(30.dp)
            .size(30.dp)) {
            onClick()
        }
        VideoView(
            file = file,
            modifier = Modifier
                .height(height = (height*0.8).dp)
                .align(Alignment.Center)
        )
    }
}

@Composable
fun VideoView(
    file: File,
    modifier:Modifier
) {

    val context = LocalContext.current
    val videoView = VideoView(context)

    videoView.setMediaController(MediaController(context))
    videoView.setVideoPath(file.toString())

    AndroidView(
        factory = {ctx->
            videoView
                  },
        modifier = modifier
        )
}
