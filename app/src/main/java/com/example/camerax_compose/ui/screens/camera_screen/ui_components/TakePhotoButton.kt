package com.example.camerax_compose.ui.screens.camera_screen.ui_components

import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.camerax_compose.ui.screens.camera_screen.CameraViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.camerax_compose.ui.screens.MainNavGraphs
import com.example.camerax_compose.utils.collectFlows
import kotlinx.coroutines.flow.collect


@Composable
fun TakePhotoButton(
    vm:CameraViewModel,
    modifier: Modifier,
    mainNav:NavController
) {
    val enabled by remember {
        vm.maskPanelVisible
    }
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp
    val height = configuration.screenHeightDp

    val pxWidth = with(LocalDensity.current){width.dp.toPx().toInt()} * 2 / 3
    val pxHeight = with(LocalDensity.current){height.dp.toPx().toInt()} * 2 / 3
    val isRecording by remember {
        vm.isRecording
    }
    Button(
        onClick = {
            if (!isRecording)
                vm.startVideo(pxWidth, pxHeight)
            else {
                vm.stopVideo()
                mainNav.navigate(MainNavGraphs.VIDEO_SCREEN.name)
            }
        },
        enabled = !enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Red,
            disabledBackgroundColor = Color.Red
        ),
        modifier = modifier,
        shape = CircleShape,
    ){}



}