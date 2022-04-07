package com.example.camerax_compose.ui.screens.camera_screen.ui_components

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.camerax_compose.ui.screens.camera_screen.CameraViewModel
import com.example.camerax_compose.R

@Composable
fun SwitchCameraButton(
    vm:CameraViewModel,
    modifier: Modifier
) {
    IconButton(
        onClick = { vm.switchCamera() },
        modifier = modifier) {
        Icon(
            painter = painterResource(id = R.drawable.camera_switch),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )
    }
}